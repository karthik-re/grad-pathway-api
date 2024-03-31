package org.example.gradpathway.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class UserDataDTO {

    private String mobile;

    private String address;

    private int experience;

    private Date dob;
}
