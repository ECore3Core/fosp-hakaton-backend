package com.tinkov.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinkov.profile.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
