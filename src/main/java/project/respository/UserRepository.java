package project.respository;

import org.springframework.stereotype.Repository;
import project.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class UserRepository {


    private static final AtomicReference<Map<String, User>> users = new AtomicReference<>(new HashMap<>());

    public void addUsers(List<User> users) {
        UserRepository.users.get()
                .putAll(users
                        .stream()
                        .collect(Collectors.toMap(
                                User::getEmail,
                                Function.identity())));
    }
}
