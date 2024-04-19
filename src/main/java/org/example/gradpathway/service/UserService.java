package org.example.gradpathway.service;

import org.example.gradpathway.DTO.LoginDTO;
import org.example.gradpathway.DTO.RegisterDTO;
import org.example.gradpathway.DTO.UserDataDTO;
import org.example.gradpathway.DTO.UserResDTO;
import org.example.gradpathway.entity.User;

import java.util.List;

public interface UserService {

    void addUser(RegisterDTO registerDTO);

    List<UserResDTO> getAllUsers();

    UserResDTO getUserById(int id);

    void deleteUser(int id);

    boolean userExists(String email);

    void addCompanyToUser(int companyId);

    UserResDTO getMyData();
}
