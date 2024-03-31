package org.example.gradpathway.service;

import org.example.gradpathway.DTO.JobPostDTO;
import org.example.gradpathway.entity.Company;
import org.example.gradpathway.entity.JobPost;
import org.example.gradpathway.repository.CompanyRepository;
import org.example.gradpathway.repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements JobService{

    private final JobsRepository jobsRepository;

    private final CompanyRepository companyRepository;

    @Autowired
    public JobServiceImpl(JobsRepository jobsRepository, CompanyRepository companyRepository) {
        this.jobsRepository = jobsRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void addJob(JobPostDTO jobDTO) {

        Company company = companyRepository.findById(jobDTO.getCompanyId()).orElse(null);

        JobPost jobPost = JobPost.builder()
                .id(0)
                .description(jobDTO.getDescription())
                .title(jobDTO.getTitle())
                .location(jobDTO.getLocation())
                .type(jobDTO.getType())
                .experience(jobDTO.getExperience())
                .salary(jobDTO.getSalary())
                .visaSponsorship(jobDTO.isVisaSponsorship())
                .postedAt(new Date())
                .company(company)
                .user(null)
                .build();
        jobsRepository.save(jobPost);
    }

    @Override
    public void updateJob(JobPostDTO jobDTO, int id) {
        JobPost jobPost = jobsRepository.findById(id).orElse(null);
        if(jobPost != null){
            jobPost.setTitle(jobDTO.getTitle());
            jobPost.setDescription(jobDTO.getDescription());
            jobPost.setLocation(jobDTO.getLocation());
            jobPost.setType(jobDTO.getType());
            jobPost.setExperience(jobDTO.getExperience());
            jobPost.setSalary(jobDTO.getSalary());
            jobPost.setVisaSponsorship(jobDTO.isVisaSponsorship());
            jobsRepository.save(jobPost);
        }
    }

    @Override
    public void deleteJob(int id) {
        jobsRepository.deleteById(id);
    }

    @Override
    public List<JobPost> getAllJobs() {
        return jobsRepository.findAll();
    }

    @Override
    public JobPost getJobById(int id) {
        return jobsRepository.findById(id).orElse(null);
    }

    @Override
    public List<JobPost> getJobsByTitle(String title) {
        return jobsRepository.findAllByTitleLikeIgnoreCase(title);
    }

    @Override
    public List<JobPost> getJobsByLocation(String location) {
        return jobsRepository.findAllByLocationLikeIgnoreCase(location);
    }

    @Override
    public List<JobPost> getJobsByType(String type) {
        return  jobsRepository.findAllByTypeLikeIgnoreCase(type);
    }

    @Override
    public List<JobPost> getJobsByExperience(int experience) {
        return jobsRepository.findAllByExperienceLessThanEqual(experience);
    }

    @Override
    public List<JobPost> getJobsBySalary(int salary) {
        return  jobsRepository.findAllBySalaryLessThanEqual(salary);
    }

    @Override
    public List<JobPost> getJobsByVisaSponsorship(boolean visaSponsorship) {
        return jobsRepository.findAllByVisaSponsorship(visaSponsorship);
    }

    @Override
    public List<JobPost> getJobsByPostedAtAfter(Date postedAt) {
        return jobsRepository.findAllByPostedAtAfter(postedAt);
    }
}
