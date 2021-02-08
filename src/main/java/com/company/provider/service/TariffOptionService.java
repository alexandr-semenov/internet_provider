package com.company.provider.service;

import com.company.provider.dto.TariffDto;
import com.company.provider.dto.TariffOptionDto;
import com.company.provider.entity.Tariff;
import com.company.provider.entity.TariffOption;
import com.company.provider.exeption.RestException;
import com.company.provider.repository.TariffOptionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TariffOptionService {
    private final TariffOptionRepository tariffOptionRepository;

    public TariffOptionService(TariffOptionRepository tariffItemRepository) {
        this.tariffOptionRepository = tariffItemRepository;
    }

    public TariffOption loadTariffOptionById(Long id) {
        return tariffOptionRepository.findById(id).orElseThrow(() -> new RestException("tariff_option_not_found_exception", null, true, false));
    }

    public void createTariffOption(TariffOptionDto tariffOptionDto, Tariff tariff) {
        TariffOption item = new TariffOption();
        item.setItem(tariffOptionDto.getOption());
        item.setTariff(tariff);

        tariffOptionRepository.save(item);
    }

    public void createTariffOption(TariffDto tariffDto, Tariff tariff) {
        List<TariffOption> tariffItems = new ArrayList<>();

        for (TariffOptionDto option : tariffDto.getTariffOption()) {
            TariffOption item = new TariffOption();
            item.setItem(option.getOption());
            item.setTariff(tariff);
            tariffItems.add(item);
        }

        tariffOptionRepository.saveAll(tariffItems);
    }

    public void updateTariffOption(TariffOption tariffItem, TariffOptionDto tariffOptionDto) {
        tariffItem.setItem(tariffOptionDto.getOption());
        tariffOptionRepository.save(tariffItem);
    }

    public void deleteTariffOption(TariffOption tariffOption) {
        tariffOptionRepository.delete(tariffOption);
    }
}
