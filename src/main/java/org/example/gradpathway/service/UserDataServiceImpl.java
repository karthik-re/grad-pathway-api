package org.example.gradpathway.service;

import org.example.gradpathway.DTO.UserDataDTO;
import org.example.gradpathway.entity.UserData;
import org.example.gradpathway.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl implements UserDataService{

    private final UserDataRepository userDataRepository;

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }
    @Override
    public void addUserData(UserDataDTO userDataDTO) {
        UserData userData = UserData.builder()
                .id(0)
                .mobile(userDataDTO.getMobile())
                .experience(userDataDTO.getExperience())
                .location(userDataDTO.getAddress())
                .build();
    }

    @Override
    public void updateUserData(UserDataDTO userDataDTO,int id) {
        UserData userData = userDataRepository.findById(id).orElse(null);
        if(userData != null){
            userData.setMobile(userDataDTO.getMobile());
            userData.setExperience(userDataDTO.getExperience());
            userData.setLocation(userDataDTO.getAddress());
            userDataRepository.save(userData);
        }
    }

    @Override
    public UserData getUserDataByUserId(int id) {
        return userDataRepository.findByUserId(id);
    }

    @Override
    public void deleteUserData(int id) {
        userDataRepository.deleteById(id);
    }
}
