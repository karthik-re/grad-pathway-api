package org.example.gradpathway.service;

import org.example.gradpathway.DTO.UserDataDTO;
import org.example.gradpathway.entity.UserData;

import java.text.ParseException;
import java.util.List;

public interface UserDataService {

    void addUserData(UserDataDTO userDataDTO) throws ParseException;

    void updateUserData(UserDataDTO userDataDTO,int id) throws ParseException;
    UserData getUserDataByUserId(int id);

    void deleteUserData(int id);
}
