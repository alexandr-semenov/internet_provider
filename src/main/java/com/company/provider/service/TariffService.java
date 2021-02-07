package com.company.provider.service;

import com.company.provider.exeption.RestException;
import com.company.provider.entity.Tariff;
import com.company.provider.repository.TariffRepository;
import org.springframework.stereotype.Service;

@Service
public class TariffService {
    private TariffRepository tariffRepository;

    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    Tariff loadTariffById(Long id) {
        return tariffRepository.findById(id).orElseThrow(() -> new RestException("couldn't find tariff", null, true, false));
    }
}
