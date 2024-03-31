package org.example.gradpathway.service;

import org.example.gradpathway.DTO.CompanyDTO;
import org.example.gradpathway.entity.Company;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

    Company getCompanyById(int id);
    void addCompany(CompanyDTO companyDTO);

    void updateCompany(CompanyDTO companyDTO, int id);

    void deleteCompany(int id);

    List<Company> getCompany(String name);

    List<Company> getAllCompanies();

    List<Company> getCompanyByLocation(String location);

    List<Company> getCompanyBySize(int size);

    String uploadImage(MultipartFile file,int id) throws IOException;

    byte[] getImage(int id) throws IOException;


}
