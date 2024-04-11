package org.example.gradpathway.service;

import org.example.gradpathway.DTO.CompanyDTO;
import org.example.gradpathway.entity.Company;
import org.example.gradpathway.entity.User;
import org.example.gradpathway.repository.CompanyRepository;
import org.example.gradpathway.util.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final AuthenticationDetails authenticationDetails;

    private final String BASE_PATH = "C:" + File.separator + "Users" + File.separator + "karth" +
            File.separator + "Desktop" + File.separator + "FileSystemStorage" + File.separator;


    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,AuthenticationDetails authenticationDetails) {
        this.authenticationDetails = authenticationDetails;
        this.companyRepository = companyRepository;
    }

    @Override
    public Company getCompanyById(int id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Override
    public void addCompany(CompanyDTO companyDTO) {
        Company company = Company.builder()
                .id(0)
                .logo(null)
                .description(companyDTO.getDescription())
                .url(companyDTO.getUrl())
                .name(companyDTO.getName())
                .location(companyDTO.getLocation())
                .size(companyDTO.getSize())
                .build();
        companyRepository.save(company);
    }

    @Override
    public void updateCompany(CompanyDTO companyDTO, int id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        User user = authenticationDetails.getUser().orElseThrow(() -> new RuntimeException("Unauthorized access"));
        if(user.getCompany().getId()!=id && !user.getRole().equals("ADMIN")){
            throw new RuntimeException("Unauthorized access");
        }
            company.setName(companyDTO.getName());
            company.setLocation(companyDTO.getLocation());
            company.setUrl(companyDTO.getUrl());
            company.setDescription(companyDTO.getDescription());
            company.setSize(companyDTO.getSize());
            companyRepository.save(company);
    }

    @Override
    public void deleteCompany(int id) {
        companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        User user = authenticationDetails.getUser().orElseThrow(() -> new RuntimeException("Unauthorized access"));
        if(user.getCompany().getId()!=id && !user.getRole().equals("ADMIN")){
            throw new RuntimeException("Unauthorized access");
        }
        companyRepository.deleteById(id);
    }

    @Override
    public List<Company> getCompany(String name) {
        return companyRepository.findAllByNameLikeIgnoreCase("%" + name + "%");
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public List<Company> getCompanyByLocation(String location) {
        return companyRepository.findAllByLocationLikeIgnoreCase(location);
    }

    @Override
    public List<Company> getCompanyBySize(int size) {
        return companyRepository.findAllBySizeLessThanEqual(size);
    }

    @Override
    public String uploadImage(MultipartFile img, int id) throws IOException {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        User user = authenticationDetails.getUser().orElseThrow(() -> new RuntimeException("Unauthorized access"));
        if(user.getCompany().getId()!=id && !user.getRole().equals("ADMIN")){
            throw new RuntimeException("Unauthorized access");
        }

        String originalImageName = img.getOriginalFilename();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePath = BASE_PATH + timeStamp + "_" + originalImageName;

        img.transferTo(new File(filePath));

        company.setLogo(filePath);
        companyRepository.save(company);

        return filePath;
    }

    @Override
    public byte[] getImage(int id) throws IOException {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        String path = company.getLogo();
        return Files.readAllBytes(new File(path).toPath());
    }

    @Override
    public boolean existsByName(String name) {
        return companyRepository.existsByName(name);
    }


}
