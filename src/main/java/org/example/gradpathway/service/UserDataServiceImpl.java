package org.example.gradpathway.service;

import org.example.gradpathway.DTO.UserDataDTO;
import org.example.gradpathway.entity.User;
import org.example.gradpathway.entity.UserData;
import org.example.gradpathway.repository.UserDataRepository;
import org.example.gradpathway.repository.UserRepository;
import org.example.gradpathway.util.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class UserDataServiceImpl implements UserDataService{

    private final UserDataRepository userDataRepository;

    private final AuthenticationDetails authenticationDetails;

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository, AuthenticationDetails authenticationDetails) {
        this.userDataRepository = userDataRepository;
        this.authenticationDetails = authenticationDetails;
    }
    @Override
    public void addUserData(UserDataDTO userDataDTO) throws ParseException {

        Optional<User> user = authenticationDetails.getUser();
        if(user.isEmpty()){
            throw new IllegalArgumentException("Unknown user");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = sdf.parse(userDataDTO.getDob());

        UserData userData = UserData.builder()
                .id(0)
                .mobile(userDataDTO.getMobile())
                .experience(userDataDTO.getExperience())
                .location(userDataDTO.getAddress())
                .user(user.get())
                .dob(dob)
                .build();
        userDataRepository.save(userData);
    }

    @Override
    public void updateUserData(UserDataDTO userDataDTO,int id) throws ParseException {
        Optional<User> user = authenticationDetails.getUser();
        if(user.isPresent() && !user.get().getRole().equals("ADMIN") && user.get().getId() != id){
            throw new IllegalArgumentException("Unauthorized");
        }
        UserData userData = userDataRepository.findById(id).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = sdf.parse(userDataDTO.getDob());
        if(userData != null){
            userData.setMobile(userDataDTO.getMobile());
            userData.setExperience(userDataDTO.getExperience());
            userData.setLocation(userDataDTO.getAddress());
            userData.setDob(dob);
            userDataRepository.save(userData);
        }
    }

    @Override
    public UserData getUserDataByUserId(int id) {
        Optional<User> user = authenticationDetails.getUser();
        if(user.isPresent() && !user.get().getRole().equals("ADMIN") && user.get().getId() != id){
            throw new IllegalArgumentException("Unauthorized");
        }
        return userDataRepository.findByUserId(id);
    }

    @Override
    public void deleteUserData(int id) {
        userDataRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User data not found"));
        userDataRepository.deleteById(id);
    }
}
