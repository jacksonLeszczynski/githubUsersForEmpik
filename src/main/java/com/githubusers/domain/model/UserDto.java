package com.githubusers.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.githubusers.domain.config.UserDtoDeserialiser;
import lombok.Data;

@JsonDeserialize(using = UserDtoDeserialiser.class)
@Data
public class UserDto {

    private int id;

    private String login;

    private String name;

    private String type;

    private String avatarUrl;

    private String createdAt;

    private String calculations;

    @JsonIgnore
    private int followers;

    @JsonIgnore
    private int public_repos;

    public void calculate() {
        if (followers == 0) {
            this.calculations = "0";
            return;
        }
        double calc = (6 / Double.valueOf(followers)) * (2 + public_repos);
        this.calculations = String.format("%.5f", calc);
    }

    public User dtoToUser(long requestCount) {
        User user = new User(login, requestCount);
        return user;
    }
}
