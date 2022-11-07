package reivosar.backendapp.Interface.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserIndexRequest(@JsonProperty("name") String name, @JsonProperty("age") int age) {
}
