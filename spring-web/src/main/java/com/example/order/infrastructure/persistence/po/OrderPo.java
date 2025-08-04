package com.example.order.infrastructure.persistence.po;

import com.example.order.domain.vo.enu.OrderStatus;
import com.example.order.domain.vo.enu.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "order_table")
public class OrderPo {
    // common fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String createdBy;
    private String lastModifiedBy;
    private String deletedBy;
    private Instant createTime;
    private Instant lastModifyTime;
    private Instant deleteTime;
    private String remark;
    private Integer deleted;
    private Integer version;

    // order specific fields
    private String orderNumber;
    private Long customerId;
    @Column(columnDefinition = "TEXT")
    private String itemsJson;
    private BigDecimal totalAmount;
    private String currency;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String shippingStreet;
    private String shippingCity;
    private String shippingState;
    private String shippingZipCode;
    private String shippingCountry;
    private String billingStreet;
    private String billingCity;
    private String billingState;
    private String billingZipCode;
    private String billingCountry;
    private Instant orderDate;
    private Instant shippedDate;
    private Instant deliveredDate;
}