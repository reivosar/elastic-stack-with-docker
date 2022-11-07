package reivosar.backendapp.Interface.api.response;

import reivosar.backendapp.domain.User;

import java.util.List;

public record SearchUsersResponse(List<User> users) {
}
