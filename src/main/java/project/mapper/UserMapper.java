package project.mapper;

import org.json.JSONArray;
import org.springframework.stereotype.Component;
import project.model.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UserMapper {

    public List<User> map(JSONArray jsonArray) {
        return IntStream.range(0, jsonArray.length())
                .boxed()
                .map(jsonArray::getJSONObject)
                .map(jsonUser -> User.builder()
                        .fullName(jsonUser.getString("fullName"))
                        .email(jsonUser.getString("email"))
                        .password(jsonUser.getString("password"))
                        .role(jsonUser.getString("role"))
                        .build())
                .collect(Collectors.toList());
    }
}
