package reivosar.backendapp.application;

import java.util.List;

import reivosar.backendapp.domain.User;

public interface UserSearchService {

    String create(final String name, final int age) throws SearchException;

    List<User> search(final UserSearchCriteria criteria) throws SearchException;

}
