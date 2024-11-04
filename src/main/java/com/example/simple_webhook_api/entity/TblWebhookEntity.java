package com.example.simple_webhook_api.entity;

import lombok.AccessLevel;
import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TBL_WEBHOOK")
@SequenceGenerator(
        name = "GEN_SEQ_TBL_WEBHOOK"
        , sequenceName = "SEQ_TBL_WEBHOOK"
        , allocationSize = 1
)

public class TblWebhookEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SEQ_TBL_WEBHOOK")
    @Id
    private long webhookSeq;

    @Basic
    private String customerId;

    @Basic
    private String webhookUrl;

    @Builder
    public TblWebhookEntity(long webhookSeq, String customerId, String webhookUrl) {
        this.webhookSeq = webhookSeq;
        this.customerId = customerId;
        this.webhookUrl = webhookUrl;
    }
    @Override
    public String toString() {
        return new StringJoiner(",",TblWebhookEntity.class.getSimpleName() + "[","]")
                .add("webhookSeq=" + webhookSeq)
                .add("customerId=" + customerId)
                .add("webhookUrl=" + webhookUrl)
                .toString();
    }
}
