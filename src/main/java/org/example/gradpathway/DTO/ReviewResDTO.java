package org.example.gradpathway.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReviewResDTO {

        private int id;

        private String title;

        private String description;

        private int rating;

        private String companyName;

        private String userFirstName;

        private String userLastName;

        private Date postedAt;
}
