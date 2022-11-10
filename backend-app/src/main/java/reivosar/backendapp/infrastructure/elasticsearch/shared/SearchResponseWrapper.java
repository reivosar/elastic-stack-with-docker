package reivosar.backendapp.infrastructure.elasticsearch.shared;

import java.util.Collections;
import java.util.List;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;

record SearchResponseWrapper<TDocument> (SearchResponse<TDocument> response) {

    boolean existsDocuments() {
        return !this.notExistsDocuments();
    }

    boolean notExistsDocuments() {
        return (this.response == null) || (this.response.hits() == null) || (this.response.hits().total() == null)
                || (this.response.hits().total().value() == 0);
    }

    long totalHitCount() {
        if (this.notExistsDocuments()) {
            return 0L;
        }
        final TotalHits total = this.response.hits().total();
        if (total == null) {
            return 0L;
        }
        return total.value();
    }

    List<TDocument> toList() {
        if (this.notExistsDocuments()) {
            return Collections.emptyList();
        }
        return this.response.hits().hits().stream().filter(hits -> hits.source() != null).map(Hit::source).toList();
    }
}
