package com.tinkov.profile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinkov.profile.repository.ServiceRepository;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public com.tinkov.profile.entity.Service createService(com.tinkov.profile.entity.Service service) {
        return serviceRepository.save(service);
    }

    public List<com.tinkov.profile.entity.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<com.tinkov.profile.entity.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public com.tinkov.profile.entity.Service updateService(Long id, com.tinkov.profile.entity.Service serviceDetails) {
        return serviceRepository.findById(id).map(service -> {
            service.setServiceName(serviceDetails.getServiceName());
            service.setDescription(serviceDetails.getDescription());
            service.setPrice(serviceDetails.getPrice());
            service.setUpdatedAt(serviceDetails.getUpdatedAt());
            return serviceRepository.save(service);
        }).orElseThrow(() -> new RuntimeException("Service not found"));
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    public Optional<com.tinkov.profile.entity.Service> getServicesForCurrentUser(Long companyId) {
        return serviceRepository.findByCompanyId(companyId);
    }
}
