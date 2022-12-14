package reivosar.backendapp.infrastructure.elasticsearch.shared;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import reivosar.backendapp.application.SearchException;

@Service
public class ElasticsearchClientWrapper {

    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public ElasticsearchClientWrapper(final ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public void create(final String indexName, final String id, final Object document) {
        try {
            this.elasticsearchClient.index(builder -> builder
                    .index(indexName)
                    .id(id)
                    .document(document));
        } catch (final Exception e) {
            throw new SearchException("Create index error.", e);
        }
    }

    public <MODEL> List<MODEL> search(final ElasticsearchParameter criteria, final Class<MODEL> mappingClass)
            throws SearchException {
        try {
            final SearchResponseWrapper<MODEL> response = new SearchResponseWrapper<>(
                    this.elasticsearchClient.search(criteria.query(), mappingClass));
            return response.toList();
        } catch (final IOException e) {
            throw new SearchException("Search user error.", e);
        }
    }

}
