package reivosar.backendapp.Interface.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reivosar.backendapp.Interface.api.request.CreateUserIndexRequest;
import reivosar.backendapp.Interface.api.request.SearchUsersRequest;
import reivosar.backendapp.Interface.api.response.CreateUserIndexResponse;
import reivosar.backendapp.Interface.api.response.SearchUsersResponse;
import reivosar.backendapp.application.UserSearchService;
import reivosar.backendapp.domain.User;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserSearchAPI {
    
    private final UserSearchService userSearchService;
    
    @Autowired
    public UserSearchAPI(final UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }
    
    @PostMapping
    public CreateUserIndexResponse create(@RequestBody CreateUserIndexRequest request) {
        String result = userSearchService.create(request.name(), request.age());
        return new CreateUserIndexResponse(result);
    }
    
    @GetMapping
    public SearchUsersResponse search(@RequestBody SearchUsersRequest request) {
        List<User> result = userSearchService.search(request.convertTo());
        return new SearchUsersResponse(result);
    }
}
