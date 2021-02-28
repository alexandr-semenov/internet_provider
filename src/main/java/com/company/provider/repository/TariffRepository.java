package com.company.provider.repository;

import com.company.provider.entity.Tariff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findById(Long id);
}
