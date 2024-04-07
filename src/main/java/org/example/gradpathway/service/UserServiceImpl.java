package org.example.gradpathway.service;

import org.example.gradpathway.DTO.RegisterDTO;
import org.example.gradpathway.DTO.UserResDTO;
import org.example.gradpathway.entity.Company;
import org.example.gradpathway.entity.User;
import org.example.gradpathway.repository.CompanyRepository;
import org.example.gradpathway.repository.UserRepository;
import org.example.gradpathway.util.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationDetails authenticationDetails;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationDetails authenticationDetails, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationDetails = authenticationDetails;
        this.companyRepository = companyRepository;
    }
    @Override
    public void addUser(RegisterDTO registerDTO) {
        User user = User.builder()
                .id(0)
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .role(registerDTO.getRole())
                .build();

        if(user.getRole().equals("EMPLOYER")) {
            Company company = companyRepository.findById(registerDTO.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            user.setCompany(company);
        }

        userRepository.save(user);
    }

    @Override
    public List<UserResDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToUserResDTO)
                .toList();
    }

    @Override
    public UserResDTO getUserById(int id) {
        Optional<User> user = authenticationDetails.getUser();
        if(user.isPresent() && !user.get().getRole().equals("ADMIN") && user.get().getId() != id) {
            throw new RuntimeException("Unauthorized");
        }
        return convertToUserResDTO(
                userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public void deleteUser(int id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserResDTO convertToUserResDTO(User user) {
        return UserResDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .companyName(user.getCompany() != null ? user.getCompany().getName() : null)
                .build();
    }
}
