package org.example.gradpathway.repository;

import org.example.gradpathway.entity.JobPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface JobsRepository extends JpaRepository<JobPost, Integer> {

    @Query("SELECT j FROM JobPost j WHERE j.company.id = :companyId")
    List<JobPost> findAllByCompanyId(int companyId);

    List<JobPost> findAllByTitleLikeIgnoreCase (String title);

    List<JobPost> findAllByLocationLikeIgnoreCase (String location);

    List<JobPost> findAllByTypeLikeIgnoreCase (String type);

    List<JobPost> findAllByExperienceLessThanEqual (int experience);

    List<JobPost> findAllBySalaryLessThanEqual (int salary);

    List<JobPost> findAllByVisaSponsorship (boolean visaSponsorship);

    List<JobPost> findAllByPostedAtAfter(Date postedAt);
}
