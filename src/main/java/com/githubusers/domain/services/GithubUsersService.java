package com.githubusers.domain.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubusers.domain.model.User;
import com.githubusers.domain.model.UserDto;
import com.githubusers.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
@Service
public class GithubUsersService {

    private String USERS_LIST_URI = "https://api.github.com/users/%s";

    private final String BEARER_TOKEN;
    private ObjectMapper mapper;
    private HttpURLConnection conn;

    @Autowired
    private UserRepository userRepository;

    public GithubUsersService(@Value("${github-rest.token}") String bearer_token) {
        BEARER_TOKEN = bearer_token;
        this.mapper = new ObjectMapper();
    }

    @Transactional
    public Optional<UserDto> findUserByLogin(String login) throws IOException {
        log.info("Starting load User content..");
        JsonNode rootNode = getContent(new URL(String.format(USERS_LIST_URI, login)));
        UserDto userDto = mapper.readValue(rootNode.toString(), UserDto.class);
        log.info("Github user readed");
        saveUserToDb(userDto);
        log.info("Request saved to db");

        return Optional.ofNullable(userDto);
    }

    @Transactional
    void saveUserToDb(UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(userDto.getLogin());
        long requestCount = 1l;
        if (existingUser.isPresent()) {
            requestCount = existingUser.get().getRequest_count();
            requestCount++;
        }
        userRepository.save(userDto.dtoToUser(requestCount));

    }

    private JsonNode getContent(URL url) throws IOException {
        initConn(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        conn.disconnect();

        return mapper.readTree(sb.toString());
    }

    private void initConn(URL url) throws IOException {
        conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", BEARER_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
    }

}
