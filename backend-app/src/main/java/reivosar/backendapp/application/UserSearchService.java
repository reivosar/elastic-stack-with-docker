package reivosar.backendapp.application;

import reivosar.backendapp.domain.User;

import java.util.List;

public interface UserSearchService {
    
    String create(final String name, final int age) throws SearchException;
    
    List<User> search(UserSearchCriteria criteria) throws SearchException;
    
}
