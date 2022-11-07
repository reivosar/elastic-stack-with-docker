package reivosar.backendapp.infrastructure.elasticsearch.shared;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reivosar.backendapp.application.SearchException;
import reivosar.backendapp.application.UserSearchCriteria;

import java.io.IOException;
import java.util.List;

@Service
public class ElasticsearchClientWrapper {
    
    private final ElasticsearchClient elasticsearchClient;
    
    @Autowired
    public ElasticsearchClientWrapper(final ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }
    
    public void create(final String indexName, final String id, final Object document) {
        try {
            elasticsearchClient.index(builder -> builder
                    .index(indexName)
                    .id(id)
                    .document(document));
        } catch (Exception e) {
            throw new SearchException("Create index error.", e);
        }
    }
    
    public <MODEL> List<MODEL> search(final String indexName, final Class<MODEL> mappingClass, final UserSearchCriteria criteria) throws SearchException {
        try {
            final SearchResponseWrapper<MODEL> response = new SearchResponseWrapper(
                    elasticsearchClient.search(s -> s
                                    .index(indexName)
                                    .query(q -> q
                                            .match(t -> t
                                                    .field("name")
                                                    .query(criteria.name())
                                            )
                                    ),
                            mappingClass
                    ));
            return response.toList();
        } catch (IOException e) {
            throw new SearchException("Search user error.", e);
        }
    }
    
}
