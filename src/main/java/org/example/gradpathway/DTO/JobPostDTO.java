package org.example.gradpathway.DTO;

import lombok.Data;

@Data
public class JobPostDTO {

    private String title;
    private String description;
    private String location;
    private String type;
    private int experience;
    private int salary;
    private boolean visaSponsorship;
    private int companyId;

}
