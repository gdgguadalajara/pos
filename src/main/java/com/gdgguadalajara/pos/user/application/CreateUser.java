package com.gdgguadalajara.pos.user.application;

import com.gdgguadalajara.pos.user.model.User;
import com.gdgguadalajara.pos.user.model.dto.CreateUserRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateUser {

    public User run(CreateUserRequest request) {
        var user = new User();
        user.name = request.name();
        user.email = request.email();
        user.persistAndFlush();
        return user;
    }
}
