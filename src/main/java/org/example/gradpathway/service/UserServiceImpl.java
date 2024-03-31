package org.example.gradpathway.service;

import org.example.gradpathway.DTO.RegisterDTO;
import org.example.gradpathway.entity.User;
import org.example.gradpathway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void addUser(RegisterDTO registerDTO) {
        User user = User.builder()
                .id(0)
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .role(registerDTO.getRole())
                .build();

        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
