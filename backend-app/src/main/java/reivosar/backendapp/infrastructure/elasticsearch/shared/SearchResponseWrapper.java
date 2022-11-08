package reivosar.backendapp.infrastructure.elasticsearch.shared;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;

import java.util.Collections;
import java.util.List;

public record SearchResponseWrapper<TDocument> (SearchResponse<TDocument> response) {

	public boolean existsDocuments() {
		return !notExistsDocuments();
	}

	public boolean notExistsDocuments() {
		return (response == null) || (response.hits() == null) || (response.hits().total() == null)
				|| (response.hits().total().value() == 0);
	}

	public long totalHitCount() {
		if (notExistsDocuments()) {
			return 0L;
		}
		TotalHits total = response.hits().total();
		if (total == null) {
			return 0L;
		}
		return total.value();
	}

	public List<TDocument> toList() {
		if (notExistsDocuments()) {
			return Collections.emptyList();
		}
		return response.hits().hits().stream().filter(hits -> hits.source() != null).map(Hit::source).toList();
	}
}
