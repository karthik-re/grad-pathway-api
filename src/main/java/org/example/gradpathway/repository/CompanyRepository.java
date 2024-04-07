package org.example.gradpathway.repository;

import org.example.gradpathway.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findAllByNameLikeIgnoreCase(String name);

    List<Company> findAllByLocationLikeIgnoreCase(String location);

    List<Company> findAllBySizeLessThanEqual(int size);

    boolean existsByName(String name);
}
