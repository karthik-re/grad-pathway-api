package org.example.gradpathway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private int experience;

    private String mobile;

    private Date dob;

    private String location;

    //to add list of skills in future
}
