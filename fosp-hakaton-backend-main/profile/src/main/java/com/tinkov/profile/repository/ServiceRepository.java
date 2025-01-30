package com.tinkov.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinkov.profile.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByCompanyId(Long companyId);
}
