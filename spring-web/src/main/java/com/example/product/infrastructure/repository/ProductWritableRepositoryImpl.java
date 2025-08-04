package com.example.product.infrastructure.repository;

import com.example.product.domain.entity.Product;
import com.example.product.domain.repository.writable.ProductWritableRepository;
import com.example.product.infrastructure.persistence.dao.ProductDao;
import com.example.product.infrastructure.persistence.mapper.ProductMapper;
import com.example.product.infrastructure.persistence.po.ProductPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class ProductWritableRepositoryImpl implements ProductWritableRepository<Product, Long> {
    private final ProductDao productDao;

    @Override
    public void modifyPrice(final Product data) {
        productDao
                .findById(data.getId())
                .ifPresent(element -> {
                    element.setPriceAmount(data.getPrice().getAmount());
                    element.setPriceCurrency(data.getPrice().getCurrency());
                    element.setLastModifyTime(Instant.now());

                    productDao.save(element);
                });
    }

    @Override
    public void modifyStock(final Product data) {
        productDao
                .findById(data.getId())
                .ifPresent(element -> {
                    element.setStockQuantity(data.getStockQuantity());
                    element.setLastModifyTime(Instant.now());

                    productDao.save(element);
                });
    }

    @Override
    public void remove(final Long id) {
        productDao.deleteById(id);
    }

    @Override
    public Product save(final Product data) {
        final ProductPo po = ProductMapper.toNewPo(data);
        final ProductPo save = productDao.save(po);

        return ProductMapper.toEntity(save);
    }

    @Override
    public void markDeleted(final Long id) {
        productDao
                .findById(id)
                .ifPresent(element -> {
                    element.setDeleted(1);
                    element.setDeleteTime(Instant.now());

                    productDao.save(element);
                });
    }
}