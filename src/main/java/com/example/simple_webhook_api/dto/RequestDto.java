package com.example.simple_webhook_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

@Getter
@Setter
public class RequestDto {
    private String orderId;

    private String customerId;

    private String orderDate;

    private Integer totalAmount;

    private String paymentMethod;

    private List<Items> items;

    public static class Items {
        private String itemId;
        private String itemName;
        private Integer quantity;
        private Integer price;

        @Builder
        public Items(String itemId, String itemName, Integer quantity, Integer price) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
        }

        @Override
        public String toString() {
            return new StringJoiner(",",Items.class.getSimpleName() + "[","]")
                    .add("itemId='" + itemId + "'")
                    .add("itemName='" + itemName + "'")
                    .add("quantity='" + quantity + "'")
                    .add("price='" + price + "'")
                    .toString();
        }
    }

    @Builder
    public RequestDto(String orderId, String customerId, String orderDate, Integer totalAmount, String paymentMethod, List<Items> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.items = items;
    }

    @Override
    public String toString() {
        return new StringJoiner(",", Items.class.getSimpleName() + "[","]")
                .add("orderId='" + orderId + "'")
                .add("customerId='" + customerId + "'")
                .add("orderDate='" + orderDate + "'")
                .add("totalAmount='" + totalAmount + "'")
                .add("paymentMethod='" + paymentMethod + "'")
                .add("items='" + items + "'")
                .toString();
    }

    public LocalDateTime getParsedOrderDate() {
        return getLocalDateTime(orderDate);
    }
    public static LocalDateTime getLocalDateTime(String time) {
        if (!org.springframework.util.StringUtils.hasLength(time))
            return null;
        if (!time.matches("\\d{12}"))  // 12자리 문자열 체크
            return null;

        // DateTimeFormatter를 사용하여 문자열을 LocalDateTime으로 변환
        DateTimeFormatter formatter_yyMMddHHmmss = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return LocalDateTime.parse(time, formatter_yyMMddHHmmss);


    }
}
