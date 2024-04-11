package org.example.gradpathway.service;

import org.example.gradpathway.DTO.JobPostDTO;
import org.example.gradpathway.DTO.JobPostResDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface JobService {

    void addJob(JobPostDTO jobDTO);

    void updateJob(JobPostDTO jobDTO, int id);

    void deleteJob(int id);

    List<JobPostResDTO> getAllJobs();

    JobPostResDTO getJobById(int id);

    List<JobPostResDTO> getJobsByTitle(String title);

    List<JobPostResDTO> getJobsByLocation(String location);

    List<JobPostResDTO> getJobsByType(String type);

    List<JobPostResDTO> getJobsByExperience(int experience);

    List<JobPostResDTO> getJobsBySalary(int salary);

    List<JobPostResDTO> getJobsByVisaSponsorship(boolean visaSponsorship);

    List<JobPostResDTO> getJobsByPostedAtAfter(String postedAt) throws ParseException;
}
