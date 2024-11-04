package com.example.simple_webhook_api.dto;

import jakarta.websocket.OnClose;
import lombok.*;

import java.util.StringJoiner;

@Getter
@Setter
public class WebhookRequestDto {
    private String orderId;
    private String customerId;
    private String status;
    private String message;

    public WebhookRequestDto() {}
    @Builder
    public WebhookRequestDto(String orderId, String customerId, String status, String message) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(",",WebhookRequestDto.class.getSimpleName() + "[","]")
                .add("orderId='" + orderId +"'")
                .add("customerId='" + customerId +"'")
                .add("status='" + status +"'")
                .add("message='" + message +"'")
                .toString();
    }
}
