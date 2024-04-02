package africa.Semicolon.eStore.services;

import africa.Semicolon.eStore.data.models.User;
import africa.Semicolon.eStore.data.repositories.Users;
import africa.Semicolon.eStore.dtos.requests.RegisterRequest;
import africa.Semicolon.eStore.dtos.responses.RegisterResponse;
import africa.Semicolon.eStore.exceptions.InvalidArgumentException;
import africa.Semicolon.eStore.exceptions.UserExistsException;
import africa.Semicolon.eStore.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.Semicolon.eStore.utils.Cleaner.lowerCaseValueOf;
import static africa.Semicolon.eStore.utils.Cryptography.encode;
import static africa.Semicolon.eStore.utils.Mapper.map;
import static africa.Semicolon.eStore.utils.Mapper.mapRegisterResponseWith;

@Service
public final class UserServicesImpl implements UserServices {
    @Autowired
    private Users users;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        validate(registerRequest);
        User newUser = map(registerRequest);
        User savedUser = users.save(newUser);
        return mapRegisterResponseWith(savedUser);
    }

    private User findUserBy(String username) {
        username = lowerCaseValueOf(username);
        User foundUser = users.findByUsername(username);
        if (foundUser == null) throw new UserNotFoundException(String.format("User with '%s' username not found", username));
        return foundUser;
    }

    private void validateUniqueUsername(RegisterRequest registerRequest) {
        String username = lowerCaseValueOf(registerRequest.getUsername());
        boolean userExists = users.existsByUsername(username);
        if (userExists) throw new UserExistsException(String.format("%s already exists", username));
    }

    private static void validateBlank(RegisterRequest registerRequest) {
        boolean isBlank = registerRequest.getUsername().isBlank() || registerRequest.getPassword().isBlank();
        if (isBlank) throw new InvalidArgumentException("Registration details cannot be blank");
    }

    private void validate(RegisterRequest registerRequest) {
        validateBlank(registerRequest);
        validateUniqueUsername(registerRequest);
    }
}
