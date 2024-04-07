package org.example.gradpathway.controller;

import jakarta.validation.Valid;
import org.example.gradpathway.DTO.CompanyDTO;
import org.example.gradpathway.entity.Company;
import org.example.gradpathway.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCompany(@Valid @RequestBody CompanyDTO company) {
        if(companyService.existsByName(company.getName())) {
            return ResponseEntity.badRequest().body("Company already exists");
        }
        companyService.addCompany(company);
        return ResponseEntity.ok("Company added successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCompany(int id) {
        try{
            companyService.deleteCompany(id);
            return ResponseEntity.ok("Company deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable int id) {
        Company company;
        try {
            company = companyService.getCompanyById(id);
            return ResponseEntity.ok(company);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCompany(@Valid @RequestBody CompanyDTO company, @PathVariable int id) {
        try{
            companyService.updateCompany(company, id);
            return ResponseEntity.ok("Company updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/company/name")
    public ResponseEntity<List<Company>> getCompany(@RequestParam String name) {
        return ResponseEntity.ok(companyService.getCompany(name));
    }

    @GetMapping("/company/location")
    public ResponseEntity<List<Company>> getCompanyByLocation(@RequestParam String location) {
        return ResponseEntity.ok(companyService.getCompanyByLocation(location));
    }

    @GetMapping("/company/size")
    public ResponseEntity<List<Company>> getCompanyBySize(@RequestParam int size) {
        return ResponseEntity.ok(companyService.getCompanyBySize(size));
    }

    @PostMapping("/image/")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file
            , @RequestParam("id") int id)
    {
        try {
            String s = companyService.uploadImage(file, id);
            return ResponseEntity.ok(s);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        try {
            byte[] image = companyService.getImage(id);
            return ResponseEntity.status(200)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
