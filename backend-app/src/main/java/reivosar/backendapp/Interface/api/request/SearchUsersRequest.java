package reivosar.backendapp.Interface.api.request;

import reivosar.backendapp.application.UserSearchCriteria;

public record SearchUsersRequest(String id, String name, int age) {

	public UserSearchCriteria convertTo() {
		return new UserSearchCriteria(id, name, age);
	}
}
