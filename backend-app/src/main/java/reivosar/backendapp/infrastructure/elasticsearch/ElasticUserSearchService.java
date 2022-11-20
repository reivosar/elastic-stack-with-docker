package reivosar.backendapp.infrastructure.elasticsearch;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reivosar.backendapp.application.SearchException;
import reivosar.backendapp.application.UserSearchCriteria;
import reivosar.backendapp.application.UserSearchService;
import reivosar.backendapp.domain.User;
import reivosar.backendapp.infrastructure.elasticsearch.shared.ElasticsearchClientWrapper;
import reivosar.backendapp.infrastructure.elasticsearch.shared.ElasticsearchParameter;

@Service
public class ElasticUserSearchService implements UserSearchService {

    private static final String INDEX_NAME = "users";

    private final ElasticsearchClientWrapper elasticsearchClient;

    @Autowired
    public ElasticUserSearchService(final ElasticsearchClientWrapper elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public String create(final String name, final int age) {
        try {
            final String id = UUID.randomUUID().toString();
            this.elasticsearchClient.create(INDEX_NAME, id, new User(id, name, age));
            return id;
        } catch (final Exception e) {
            throw new SearchException("Create index error.", e);
        }
    }

    @Override
    public List<User> search(final UserSearchCriteria criteria) throws SearchException {
        try {
            return this.elasticsearchClient.search(
                    new ElasticsearchParameter.Builder(INDEX_NAME)
                            .equal("id", criteria.id())
                            .equal("name", criteria.name())
                            .equal("age", criteria.age())
                            .build(),
                    User.class);
        } catch (final Exception e) {
            throw new SearchException("Search user error.", e);
        }
    }
}
