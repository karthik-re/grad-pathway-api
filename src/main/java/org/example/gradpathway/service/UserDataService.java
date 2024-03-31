package org.example.gradpathway.service;

import org.example.gradpathway.DTO.UserDataDTO;
import org.example.gradpathway.entity.UserData;

import java.util.List;

public interface UserDataService {

    void addUserData(UserDataDTO userDataDTO);

    void updateUserData(UserDataDTO userDataDTO,int id);
    UserData getUserDataByUserId(int id);

    void deleteUserData(int id);
}
