package com.githubusers.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Service
public class UserDataService {

    private JSONParser jsonParser = new JSONParser();

    public void loadJsonFile() throws IOException, ParseException {
        FileReader reader = new FileReader("usersData.json");
        Object obj = jsonParser.parse(reader);

        JSONArray employeeList = (JSONArray) obj;

    }
}
