package reivosar.backendapp.infrastructure.elasticsearch.shared;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

	@Bean
	public ElasticsearchClient elasticsearchClient() {

		final BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
		basicCredentialsProvider.setCredentials(
				AuthScope.ANY, new UsernamePasswordCredentials("elastic", "password"));

		final RestClient restClient = RestClient
				.builder(new HttpHost("localhost", 9200, "http"))
				.setHttpClientConfigCallback(hc -> hc
						.setDefaultCredentialsProvider(basicCredentialsProvider))
				.build();

		final ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

		return new ElasticsearchClient(transport);
	}
}