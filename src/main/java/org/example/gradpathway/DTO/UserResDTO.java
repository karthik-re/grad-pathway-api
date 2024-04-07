package org.example.gradpathway.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResDTO {

    private int id;

    private String email;

    private String role;

    private String firstName;

    private String lastName;

    private String companyName;
}
