package org.example.gradpathway.service;

import org.example.gradpathway.DTO.JobPostDTO;
import org.example.gradpathway.entity.JobPost;

import java.util.Date;
import java.util.List;

public interface JobService {

    void addJob(JobPostDTO jobDTO);

    void updateJob(JobPostDTO jobDTO, int id);

    void deleteJob(int id);

    List<JobPost> getAllJobs();

    JobPost getJobById(int id);

    List<JobPost> getJobsByTitle(String title);

    List<JobPost> getJobsByLocation(String location);

    List<JobPost> getJobsByType(String type);

    List<JobPost> getJobsByExperience(int experience);

    List<JobPost> getJobsBySalary(int salary);

    List<JobPost> getJobsByVisaSponsorship(boolean visaSponsorship);

    List<JobPost> getJobsByPostedAtAfter(Date postedAt);
}
