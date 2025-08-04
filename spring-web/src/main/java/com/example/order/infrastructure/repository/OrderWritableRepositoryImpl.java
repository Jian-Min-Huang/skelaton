package com.example.order.infrastructure.repository;

import com.example.order.domain.entity.Order;
import com.example.order.domain.repository.writable.OrderWritableRepository;
import com.example.order.domain.vo.enu.OrderStatus;
import com.example.order.infrastructure.persistence.dao.OrderDao;
import com.example.order.infrastructure.persistence.mapper.OrderMapper;
import com.example.order.infrastructure.persistence.po.OrderPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class OrderWritableRepositoryImpl implements OrderWritableRepository<Order, Long> {
    private final OrderDao orderDao;

    @Override
    public void modifyStatus(final Order data) {
        orderDao
                .findById(data.getId())
                .ifPresent(element -> {
                    element.setStatus(data.getStatus());
                    element.setLastModifyTime(Instant.now());

                    // Update shipping/delivery dates based on status
                    if (data.getStatus() == OrderStatus.SHIPPED && element.getShippedDate() == null) {
                        element.setShippedDate(Instant.now());
                    } else if (data.getStatus() == OrderStatus.DELIVERED && element.getDeliveredDate() == null) {
                        element.setDeliveredDate(Instant.now());
                    }

                    orderDao.save(element);
                });
    }

    @Override
    public void modifyShippingAddress(final Order data) {
        orderDao
                .findById(data.getId())
                .ifPresent(element -> {
                    element.setShippingStreet(data.getShippingAddress().getStreet());
                    element.setShippingCity(data.getShippingAddress().getCity());
                    element.setShippingState(data.getShippingAddress().getState());
                    element.setShippingZipCode(data.getShippingAddress().getZipCode());
                    element.setShippingCountry(data.getShippingAddress().getCountry());
                    element.setLastModifyTime(Instant.now());

                    orderDao.save(element);
                });
    }

    @Override
    public void cancel(final Long id) {
        orderDao
                .findById(id)
                .ifPresent(element -> {
                    element.setStatus(OrderStatus.CANCELLED);
                    element.setLastModifyTime(Instant.now());

                    orderDao.save(element);
                });
    }

    @Override
    public Order save(final Order data) {
        final OrderPo po = OrderMapper.toNewPo(data);
        final OrderPo save = orderDao.save(po);

        return OrderMapper.toEntity(save);
    }

    @Override
    public void markDeleted(final Long id) {
        orderDao
                .findById(id)
                .ifPresent(element -> {
                    element.setDeleted(1);
                    element.setDeleteTime(Instant.now());

                    orderDao.save(element);
                });
    }
}