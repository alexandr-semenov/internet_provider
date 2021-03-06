package com.company.provider.service;

import com.company.provider.dto.TariffDto;
import com.company.provider.entity.Product;
import com.company.provider.entity.Tariff;
import com.company.provider.exeption.RestException;
import com.company.provider.repository.TariffRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

@Service
public class TariffService {
    private final TariffRepository tariffRepository;
    private final ProductService productService;
    private final TariffOptionService tariffItemService;
    private final EntityManager entityManager;

    public TariffService(
            TariffRepository tariffRepository,
            ProductService productService,
            TariffOptionService tariffItemService,
            EntityManager entityManager
    ) {
        this.tariffRepository = tariffRepository;
        this.productService = productService;
        this.tariffItemService = tariffItemService;
        this.entityManager = entityManager;
    }

    public List<Tariff> loadAllTariffs() {
        return tariffRepository.findAll();
    }

    public List<Tariff> loadMultipleTariff(List<Long> ids) {
        return entityManager
                .createQuery("select t from Tariff t where t.id in (:tariffIds)", Tariff.class)
                .setParameter("tariffIds", ids)
                .getResultList();
    }

    public Tariff loadTariffById(Long id) {
        return tariffRepository.findById(id).orElseThrow(() -> new RestException("couldn't find tariff", null, true, false));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createTariff(TariffDto tariffDto) {
        Product product = productService.getProduct(tariffDto.getProductId());

        Tariff tariff = Tariff.builder()
                .setName(tariffDto.getName())
                .setDescription(tariffDto.getDescription())
                .setPrice(tariffDto.getPrice())
                .setProduct(product)
                .build();

        tariffRepository.save(tariff);
        tariffItemService.createTariffOptions(tariffDto, tariff);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateTariff(TariffDto tariffDto, Tariff tariff) {
        tariff.setName(tariffDto.getName());
        tariff.setDescription(tariffDto.getDescription());
        tariff.setPrice(tariffDto.getPrice());

        Product product = productService.getProduct(tariffDto.getProductId());
        tariff.setProduct(product);
        tariffRepository.save(tariff);
    }

    public void deleteTariff(Tariff tariff) {
        tariffRepository.delete(tariff);
    }
}
