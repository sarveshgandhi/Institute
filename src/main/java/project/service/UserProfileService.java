package project.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.exception.CustomException;
import project.mapper.UserMapper;
import project.model.User;
import project.respository.UserRepository;
import project.validator.JsonValidator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class UserProfileService {

    private final JsonValidator jsonValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private static final List<String> REQUIRED_FIELDS = Arrays.asList("fullName", "email", "password", "role");
    private static final String USERS = "users";

    @Autowired
    public UserProfileService(JsonValidator jsonValidator,
                              UserMapper userMapper,
                              UserRepository userRepository) {
        this.jsonValidator = jsonValidator;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public String readUsers(MultipartFile file) {
        jsonValidator.validateFileUpload(file);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new String(file.getBytes()));
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }

        validateJsonForUsers(jsonObject);
        List<User> users = userMapper.map(jsonObject.getJSONArray(USERS));
        userRepository.saveAll(users);
        return "Users input file read and stored successfully";
    }

    private void validateJsonForUsers(JSONObject jsonObject) {
        if (!jsonObject.has(USERS) || jsonObject.getJSONArray(USERS).isEmpty()) {
            throw new CustomException("users can not be empty");
        }

        JSONArray usersJson = jsonObject.getJSONArray(USERS);
        IntStream.range(0, usersJson.length())
                .boxed()
                .forEach(index -> {
                    JSONObject user = usersJson.getJSONObject(index);
                    Optional<String> missingFieldOptional = REQUIRED_FIELDS.stream()
                            .filter(field -> !user.has(field))
                            .findFirst();
                    if (missingFieldOptional.isPresent()) {
                        throw new CustomException("Missing the field %s in the input file", missingFieldOptional.get());
                    }
                });
    }
}
