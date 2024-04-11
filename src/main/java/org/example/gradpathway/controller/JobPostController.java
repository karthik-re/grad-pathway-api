package org.example.gradpathway.controller;

import jakarta.validation.Valid;
import org.example.gradpathway.DTO.JobPostDTO;
import org.example.gradpathway.DTO.JobPostResDTO;
import org.example.gradpathway.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobPostController {

    private final JobService jobService;
    @Autowired
    public JobPostController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addJob(@Valid @RequestBody JobPostDTO jobDTO) {
        try {
            jobService.addJob(jobDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        return ResponseEntity.ok("Job added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJob(@Valid @RequestBody JobPostDTO jobDTO, @PathVariable int id) {
        try{
            jobService.updateJob(jobDTO, id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        return ResponseEntity.ok("Job updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable int id) {
        try {
            jobService.deleteJob(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        return ResponseEntity.ok("Job deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobPostResDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<?> getJobById(@PathVariable int id) {
        JobPostResDTO jobPostResDTO;
        try {
            jobPostResDTO = jobService.getJobById(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        return ResponseEntity.ok(jobPostResDTO);
    }

    @GetMapping("/job/title/{title}")
    public ResponseEntity<List<JobPostResDTO>> getJobsByTitle(@PathVariable String title) {
        return ResponseEntity.ok(jobService.getJobsByTitle(title));
    }

    @GetMapping("/job/location/{location}")
    public ResponseEntity<List<JobPostResDTO>> getJobsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(jobService.getJobsByLocation(location));
    }

    @GetMapping("/job/type/{type}")
    public ResponseEntity<List<JobPostResDTO>> getJobsByType(@PathVariable String type) {
        return ResponseEntity.ok(jobService.getJobsByType(type));
    }

    @GetMapping("/job/exp/{experience}")
    public ResponseEntity<List<JobPostResDTO>> getJobsByExperience(@PathVariable int experience) {
        return ResponseEntity.ok(jobService.getJobsByExperience(experience));
    }

    @GetMapping("/job/salary/{salary}")
    public ResponseEntity<List<JobPostResDTO>> getJobsBySalary(@PathVariable int salary) {
        return ResponseEntity.ok(jobService.getJobsBySalary(salary));
    }

    @GetMapping("/job/visa/{visaSponsorship}")
    public ResponseEntity<List<JobPostResDTO>> getJobsByVisaSponsorship(@PathVariable boolean visaSponsorship) {
        return ResponseEntity.ok(jobService.getJobsByVisaSponsorship(visaSponsorship));
    }

    @GetMapping("/job/date/{postedAt}")
    public ResponseEntity<List<JobPostResDTO>> getJobsByPostedAtAfter(@PathVariable String postedAt)
            throws ParseException {
        return ResponseEntity.ok(jobService.getJobsByPostedAtAfter(postedAt));
    }

}
