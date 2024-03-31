package org.example.gradpathway.controller;

import org.example.gradpathway.DTO.JobPostDTO;
import org.example.gradpathway.entity.JobPost;
import org.example.gradpathway.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> addJob(@RequestBody JobPostDTO jobDTO) {
        jobService.addJob(jobDTO);
        return ResponseEntity.ok("Job added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJob(@RequestBody JobPostDTO jobDTO, @PathVariable int id) {
        jobService.updateJob(jobDTO, id);
        return ResponseEntity.ok("Job updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobPost>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<JobPost> getJobById(@PathVariable int id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("/job/{title}")
    public ResponseEntity<List<JobPost>> getJobsByTitle(@PathVariable String title) {
        return ResponseEntity.ok(jobService.getJobsByTitle(title));
    }

    @GetMapping("/job/{location}")
    public ResponseEntity<List<JobPost>> getJobsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(jobService.getJobsByLocation(location));
    }

    @GetMapping("/job/type/{type}")
    public ResponseEntity<List<JobPost>> getJobsByType(@PathVariable String type) {
        return ResponseEntity.ok(jobService.getJobsByType(type));
    }

    @GetMapping("/job/exp/{experience}")
    public ResponseEntity<List<JobPost>> getJobsByExperience(@PathVariable int experience) {
        return ResponseEntity.ok(jobService.getJobsByExperience(experience));
    }

    @GetMapping("/job/salary/{salary}")
    public ResponseEntity<List<JobPost>> getJobsBySalary(@PathVariable int salary) {
        return ResponseEntity.ok(jobService.getJobsBySalary(salary));
    }

    @GetMapping("/job/visa/{visaSponsorship}")
    public ResponseEntity<List<JobPost>> getJobsByVisaSponsorship(@PathVariable boolean visaSponsorship) {
        return ResponseEntity.ok(jobService.getJobsByVisaSponsorship(visaSponsorship));
    }

    @GetMapping("/job/date/{postedAt}")
    public ResponseEntity<List<JobPost>> getJobsByPostedAtAfter(@PathVariable Date postedAt) {
        return ResponseEntity.ok(jobService.getJobsByPostedAtAfter(postedAt));
    }

}
