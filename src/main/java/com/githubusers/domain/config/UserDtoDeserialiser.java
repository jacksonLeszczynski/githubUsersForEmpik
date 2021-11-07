package com.githubusers.domain.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.githubusers.domain.model.UserDto;
import java.io.IOException;

public class UserDtoDeserialiser extends JsonDeserializer<UserDto> {

    @Override
    public UserDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);

        UserDto userDto = new UserDto();

        userDto.setId(node.path("id").asInt());
        userDto.setLogin(node.path("login").asText());
        userDto.setName(node.path("name").asText());
        userDto.setType(node.path("type").asText());
        userDto.setAvatarUrl(node.path("avatar_url").asText());
        userDto.setCreatedAt(node.path("created_at").asText());
        userDto.setFollowers(node.path("followers").asInt());
        userDto.setPublic_repos(node.path("public_repos").asInt());
        userDto.calculate();

        return userDto;
    }

}
