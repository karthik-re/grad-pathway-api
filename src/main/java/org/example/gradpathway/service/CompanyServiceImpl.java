package org.example.gradpathway.service;

import org.example.gradpathway.DTO.CompanyDTO;
import org.example.gradpathway.entity.Company;
import org.example.gradpathway.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    private final String BASE_PATH = "C:" + File.separator+"Users" + File.separator + "karth" +
            File.separator + "Desktop" + File.separator + "FileSystemStorage" + File.separator;


    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    @Override
    public Company getCompanyById(int id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.orElse(null);
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
        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent()){
            Company company1 = company.get();
            company1.setName(companyDTO.getName());
            company1.setLocation(companyDTO.getLocation());
            company1.setUrl(companyDTO.getUrl());
            company1.setDescription(companyDTO.getDescription());
            company1.setSize(companyDTO.getSize());
            companyRepository.save(company1);
        }
    }

    @Override
    public void deleteCompany(int id) {
        companyRepository.deleteById(id);
    }

    @Override
    public List<Company> getCompany(String name) {
        return companyRepository.findAllByNameLikeIgnoreCase(name);
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
    public String uploadImage(MultipartFile img,int id) throws IOException {
        String originalImageName = img.getOriginalFilename();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePath = BASE_PATH+timeStamp+"_"+originalImageName;

        img.transferTo(new File(filePath));

        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent()){
            Company company1 = company.get();
            company1.setLogo(filePath);
            companyRepository.save(company1);
        }

        return filePath;
    }

    @Override
    public byte[] getImage(int id) throws IOException {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()){
            return null;
        }
        String path = company.get().getLogo();
        return Files.readAllBytes(new File(path).toPath());
    }
}
