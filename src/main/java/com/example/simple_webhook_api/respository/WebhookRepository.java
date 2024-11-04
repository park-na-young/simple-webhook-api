package com.example.simple_webhook_api.respository;

import com.example.simple_webhook_api.entity.TblWebhookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface WebhookRepository extends JpaRepository<TblWebhookEntity, Long> {
    Optional<TblWebhookEntity> findByCustomerId(String customerId);

}
