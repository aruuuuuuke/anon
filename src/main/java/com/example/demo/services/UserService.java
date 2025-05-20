package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User updateUserById(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            // если хочешь менять пароль, раскомментируй:
//            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            return userRepository.save(user);
        }).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void assignManagerCodeToUser(Long userId, String managerCode) {
        User manager = userRepository.findByManagerCode(managerCode)
                .orElseThrow(() -> new UsernameNotFoundException("Manager not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setAssignedManagerCode(managerCode);
        userRepository.save(user);
    }


}
