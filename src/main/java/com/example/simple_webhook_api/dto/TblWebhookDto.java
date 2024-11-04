package com.example.simple_webhook_api.dto;

import lombok.*;

import java.util.StringJoiner;

@Getter
@Setter
public class TblWebhookDto {
    private String customerId;
    private String webhookUrl;

    public TblWebhookDto(String customerId, String webhookUrl) {
        this.customerId = customerId;
        this.webhookUrl = webhookUrl;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TblWebhookDto.class.getSimpleName() + "[", "]")
                .add("customerId='" + customerId + "'")
                .add("webhookUrl='" + webhookUrl + "'")
                .toString();
    }
}
