package com.company.provider.service;

import com.company.provider.entity.Status;
import com.company.provider.repository.StatusRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    Status getStatusByName(String name) {
        return statusRepository.findByName(name);
    }
}
