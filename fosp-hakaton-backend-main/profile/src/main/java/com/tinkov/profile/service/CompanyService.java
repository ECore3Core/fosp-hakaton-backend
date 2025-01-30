package com.tinkov.profile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinkov.profile.entity.Company;
import com.tinkov.profile.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company updateCompany(Long id, Company companyDetails) {
        return companyRepository.findById(id).map(company -> {
            company.setCompanyName(companyDetails.getCompanyName());
            company.setDescription(companyDetails.getDescription());
            company.setWebsite(companyDetails.getWebsite());
            company.setUpdatedAt(companyDetails.getUpdatedAt());
            return companyRepository.save(company);
        }).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(companyRepository.findByUserId(id).get().getId());
    }

    public Optional<Company> getCompanyForCurrentUser(Long userId) {
        return companyRepository.findByUserId(userId);
    }
    
}
