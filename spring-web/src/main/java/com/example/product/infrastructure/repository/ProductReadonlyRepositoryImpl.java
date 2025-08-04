package com.example.product.infrastructure.repository;

import com.example.common.data.Pagination;
import com.example.product.domain.entity.Product;
import com.example.product.domain.repository.readonly.ProductReadonlyRepository;
import com.example.product.infrastructure.persistence.dao.ProductDao;
import com.example.product.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductReadonlyRepositoryImpl implements ProductReadonlyRepository<Product, Long> {
    private final ProductDao productDao;

    @Override
    public Optional<Product> findById(final Long id) {
        return productDao.findById(id).map(ProductMapper::toEntity);
    }

    @Override
    public Pagination<Product> findAll(final Integer pageNumber, final Integer pageSize) {
        final List<Product> content = productDao
                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")))
                .stream()
                .map(ProductMapper::toEntity)
                .toList();
        final Long totalElements = productDao.count();

        return Pagination
                .<Product>builder()
                .content(content)
                .currentPage(pageNumber)
                .pageSize(pageSize)
                .totalPages((int) Math.ceil((double) totalElements / pageSize))
                .totalElements(totalElements)
                .build();
    }
}