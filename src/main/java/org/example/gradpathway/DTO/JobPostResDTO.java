package org.example.gradpathway.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JobPostResDTO {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private int experience;
    private int salary;
    private boolean visaSponsorship;
    private String companyName;
    private String userFirstName;
    private String userLastName;
    private Date postedAt;
}
