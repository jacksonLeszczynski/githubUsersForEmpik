package com.githubusers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Getter
    @Id
    private String login;

    @Getter
    @Setter
    private Long request_count;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.login == null || other.login == null) {
            return false;
        }

        return this.login.equals(other.login);
    }
}
