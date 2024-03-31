package org.example.gradpathway.repository;

import org.example.gradpathway.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    @Query("SELECT r FROM Reviews r WHERE r.company.id = :companyId")
    List<Reviews> findAllByCompanyId(int companyId);

    @Query("SELECT r FROM Reviews r WHERE r.company.id = :companyId ORDER BY r.rating DESC")
    List<Reviews> findAllByCompanyIdOrderByBest(int companyId);

    @Query("SELECT r FROM Reviews r WHERE r.company.id = :companyId ORDER BY r.rating ASC")
    List<Reviews> findAllByCompanyIdOrderByWorst(int companyId);

}
