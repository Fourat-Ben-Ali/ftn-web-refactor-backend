package main.natationtn.Services;

import lombok.RequiredArgsConstructor;
import main.natationtn.DTO.RegisterRequest;
import main.natationtn.Entities.User;
import main.natationtn.Entities.UserRoles;
import main.natationtn.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(RegisterRequest request) {
        // Check if user with email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(request.getUserRoles() != null ? request.getUserRoles() : UserRoles.USER)
                .build();

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        // Check if user exists
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found");
        }

        // If email is being changed, check if new email already exists
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent() && !existingUser.get().getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("User with this email already exists");
            }
        }

        // If password is being updated, encode it
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // Keep existing password if not provided
            existingUser.ifPresent(existing -> user.setPassword(existing.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
} 