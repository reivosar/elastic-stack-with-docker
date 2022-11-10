package reivosar.backendapp.Interface.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reivosar.backendapp.Interface.api.request.CreateUserIndexRequest;
import reivosar.backendapp.Interface.api.request.SearchUsersRequest;
import reivosar.backendapp.Interface.api.response.CreateUserIndexResponse;
import reivosar.backendapp.Interface.api.response.SearchUsersResponse;
import reivosar.backendapp.application.UserSearchService;
import reivosar.backendapp.domain.User;

@RestController
@RequestMapping("/users")
public class UserSearchAPI {

    private final UserSearchService userSearchService;

    @Autowired
    public UserSearchAPI(final UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @PostMapping
    public CreateUserIndexResponse create(@RequestBody final CreateUserIndexRequest request) {
        final String result = this.userSearchService.create(request.name(), request.age());
        return new CreateUserIndexResponse(result);
    }

    @GetMapping
    public SearchUsersResponse search(@RequestBody final SearchUsersRequest request) {
        final List<User> result = this.userSearchService.search(request.convertTo());
        return new SearchUsersResponse(result);
    }
}
