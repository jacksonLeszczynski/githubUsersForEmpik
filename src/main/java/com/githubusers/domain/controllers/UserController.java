package com.githubusers.domain.controllers;

import com.githubusers.domain.model.UserDto;
import com.githubusers.domain.services.GithubUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final GithubUsersService githubUsersService;

    @RequestMapping(
            value = "/users/{login}",
            method = GET,
            produces = "application/json")
    public Optional<UserDto> findUser(@PathVariable("login") String login) {
        try {
            return githubUsersService.findUserByLogin(login);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found: ", e);
        }
    }
}
