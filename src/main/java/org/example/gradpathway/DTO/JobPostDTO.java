package org.example.gradpathway.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobPostDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Type is required")
    private String type;

    private int experience;

    private int salary;

    private boolean visaSponsorship;

    private int companyId;

}
