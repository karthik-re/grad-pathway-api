package org.example.gradpathway.service;

import org.example.gradpathway.DTO.JobPostDTO;
import org.example.gradpathway.DTO.JobPostResDTO;
import org.example.gradpathway.entity.Company;
import org.example.gradpathway.entity.JobPost;
import org.example.gradpathway.entity.User;
import org.example.gradpathway.repository.CompanyRepository;
import org.example.gradpathway.repository.JobsRepository;
import org.example.gradpathway.util.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobsRepository jobsRepository;

    private final CompanyRepository companyRepository;

    private final AuthenticationDetails authenticationDetails;

    @Autowired
    public JobServiceImpl(JobsRepository jobsRepository, CompanyRepository companyRepository, AuthenticationDetails authenticationDetails) {
        this.jobsRepository = jobsRepository;
        this.companyRepository = companyRepository;
        this.authenticationDetails = authenticationDetails;
    }

    @Override
    public void addJob(JobPostDTO jobDTO) {

        Optional<User> user = authenticationDetails.getUser();

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Unauthorized");
        }
        Company company = companyRepository.findById(user.get().getCompany().getId())
                .orElseThrow(() -> new RuntimeException("No company associated with the user"));

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
                .user(user.get())
                .url(jobDTO.getUrl())
                .build();
        jobsRepository.save(jobPost);
    }

    @Override
    public void updateJob(JobPostDTO jobDTO, int id) {
        JobPost jobPost = jobsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        User user = authenticationDetails.getUser()
                .orElseThrow(() -> new RuntimeException("Unauthorized"));

        if (jobPost.getCompany().getId() != user.getCompany().getId() && !user.getRole().equals("ADMIN")) {
            throw new RuntimeException("Unauthorized");
        }

        jobPost.setTitle(jobDTO.getTitle());
        jobPost.setDescription(jobDTO.getDescription());
        jobPost.setLocation(jobDTO.getLocation());
        jobPost.setType(jobDTO.getType());
        jobPost.setExperience(jobDTO.getExperience());
        jobPost.setSalary(jobDTO.getSalary());
        jobPost.setVisaSponsorship(jobDTO.isVisaSponsorship());
        jobPost.setUrl(jobDTO.getUrl());
        jobsRepository.save(jobPost);

    }

    @Override
    public void deleteJob(int id) {
        JobPost jobPost = jobsRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        User user = authenticationDetails.getUser().orElseThrow(() -> new RuntimeException("Unauthorized"));
        if(jobPost.getCompany().getId() != user.getCompany().getId() && !user.getRole().equals("ADMIN")) {
            throw new RuntimeException("Unauthorized");
        }
        jobsRepository.deleteById(id);
    }

    @Override
    public List<JobPostResDTO> getAllJobs() {
        List<JobPost> jobPosts = jobsRepository.findAll();
        return jobPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JobPostResDTO getJobById(int id) {
        JobPost jobPost = jobsRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        return convertToDTO(jobPost);
    }

    @Override
    public List<JobPostResDTO> getJobsByTitle(String title) {
        return jobsRepository
                .findAllByTitleLikeIgnoreCase("%" + title + "%")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobPostResDTO> getJobsByLocation(String location) {
        return jobsRepository.findAllByLocationLikeIgnoreCase("%" + location + "%")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobPostResDTO> getJobsByType(String type) {
        return jobsRepository.findAllByTypeLikeIgnoreCase("%" + type + "%")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobPostResDTO> getJobsByExperience(int experience) {
        return jobsRepository.findAllByExperienceLessThanEqual(experience)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobPostResDTO> getJobsBySalary(int salary) {
        return jobsRepository.findAllBySalaryLessThanEqual(salary)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobPostResDTO> getJobsByVisaSponsorship(boolean visaSponsorship) {
        return jobsRepository.findAllByVisaSponsorship(visaSponsorship)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobPostResDTO> getJobsByPostedAtAfter(String postedAt) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date postedAtDate = formatter.parse(postedAt);

        return jobsRepository.findAllByPostedAtAfter(postedAtDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private JobPostResDTO convertToDTO(JobPost jobPost) {
        return JobPostResDTO.builder()
                .id(jobPost.getId())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .location(jobPost.getLocation())
                .type(jobPost.getType())
                .experience(jobPost.getExperience())
                .salary(jobPost.getSalary())
                .visaSponsorship(jobPost.isVisaSponsorship())
                .postedAt(jobPost.getPostedAt())
                .companyName(jobPost.getCompany().getName())
                .userFirstName(jobPost.getUser().getFirstName())
                .userLastName(jobPost.getUser().getLastName())
                .url(jobPost.getUrl())
                .build();
    }
}
