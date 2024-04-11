package org.example.gradpathway.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

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

    @URL(message = "Invalid URL")
    private String url;

    private int salary;

    private boolean visaSponsorship;

}
