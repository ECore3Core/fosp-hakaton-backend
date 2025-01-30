package com.tinkov.profile.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinkov.profile.entity.Company;
import com.tinkov.profile.entity.Service;
import com.tinkov.profile.service.CompanyService;
import com.tinkov.profile.service.ExternalAuthService;
import com.tinkov.profile.service.ServiceService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ExternalAuthService externalAuthService;

    private boolean validateToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring(7);
        return externalAuthService.validateToken(token);
    }

    private Long getUserId(HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);  
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring(7);
        return externalAuthService.getUserId(token);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Optional<Company>> getCompanyByUserId(@PathVariable Long id, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/get-company-id")
    public ResponseEntity<Long> getCompanyId(HttpServletRequest request){
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        return ResponseEntity.ok(companyService.getCompanyForCurrentUser(getUserId(request)).get().getId());
    }

    @PostMapping("/create-company")
    public ResponseEntity<String> createCompany(@RequestBody Company company, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        Long currentUserId = getUserId(request);
        if (!currentUserId.equals(company.getUserId())) {
            throw new RuntimeException("Access denied. You do not own this company.");
        }
        companyService.createCompany(company);
        return ResponseEntity.ok("Company created successfully");
    }

    @GetMapping("/get-company/{id}")
    public ResponseEntity<Optional<Company>> getCompanyById(@PathVariable Long id, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        Optional<Company> company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/update-company/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        Long currentUserId = getUserId(request);
        if (!currentUserId.equals(id)) {
            throw new RuntimeException("Access denied. You do not own this company.");
        }
        Company updatedCompany = companyService.updateCompany(id, company);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/delete-company/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        Long currentUserId = getUserId(request);
        if (!currentUserId.equals(id)) {
            throw new RuntimeException("Access denied. You do not own this company.");
        }
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Company deleted successfully");
    }

    @GetMapping("/service/{id}")
    public ResponseEntity<Optional<Service>> getServicesByIdUser(@PathVariable Long id, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        return ResponseEntity.ok(serviceService.getServicesForCurrentUser(companyService.getCompanyById(id).get().getId()));
    }

    @PostMapping("/create-service")
    public ResponseEntity<String> createService(@RequestBody Service service, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid."); //TODO доделать
        }
        serviceService.createService(service);
        return ResponseEntity.ok("Service created successfully");
    }

    @GetMapping("/get-service/{id}")
    public ResponseEntity<Optional<Service>> getServiceById(@PathVariable Long id, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        Optional<Service> services = serviceService.getServiceById(id);
        return ResponseEntity.ok(services);
    }

    @PutMapping("/update-service/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service serviceDetails, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        Long currentUserId = getUserId(request);
        if (!currentUserId.equals(serviceService.getServiceById(id).get().getCompany().getUserId())) {
            throw new RuntimeException("Access denied. You do not own this service.");
        }
        Service service = serviceService.updateService(id, serviceDetails);
        return ResponseEntity.ok(service);
    }

    @DeleteMapping("/ ")
    public ResponseEntity<String> deleteService(@PathVariable Long id, HttpServletRequest request) {
        if (!validateToken(request)) {
            throw new RuntimeException("Token is invalid.");
        }
        Long currentUserId = getUserId(request);
        if (!currentUserId.equals(serviceService.getServiceById(id).get().getCompany().getUserId())) {
            throw new RuntimeException("Access denied. You do not own this service.");
        }
        serviceService.deleteService(id);
        return ResponseEntity.ok("Service deleted successfully");
    }
}
