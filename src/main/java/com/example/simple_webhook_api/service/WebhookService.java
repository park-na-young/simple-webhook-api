package com.example.simple_webhook_api.service;

import com.example.simple_webhook_api.dto.*;
import com.example.simple_webhook_api.respository.WebhookRepository;
import com.example.simple_webhook_api.utils.WebClientUtils;
import org.slf4j.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;


@Service
@Component
public class WebhookService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WebhookRepository webhookRepository;
    private final WebClientUtils webClientUtils;

    public WebhookService(
            WebhookRepository webhookRepository,
            WebClientUtils webClientUtils
    ) {
        this.webhookRepository = webhookRepository;
        this.webClientUtils = webClientUtils;
    }

    /**
     * 웹훅 전송 메서드
     *
     * @param requestBody
     * @return
     */
    @Transactional
    public ResponseDto sendWebhook(RequestDto requestBody) {
        logger.info("<<< WebhookService >>> sendWebhook : requestBody = {}", requestBody);

        /*
        todo :
            1. requestBody - customerId 를 find in webhook
                1.2  getWebhookUrl
            2. webhook url 로 webclient send
         */

        // 웹훅 url을 customerId를 기반으로 조회
        String requestPath = (webhookRepository.findByCustomerId(requestBody.getCustomerId()).get().getWebhookUrl());
        logger.info("<< webhookurl >> ={}", requestPath);

        // 웹훅 url이 없으면 오류 응답 반환
        if (requestPath == null) {
            return ResponseDto.builder().code("E001").message("등록된 웹훅 url이 존재하지 않습니다.").build();
        }
        // 웹훅 요청 본문 생성
        WebhookRequestDto webhookRequestDto = new WebhookRequestDto();
        webhookRequestDto.setOrderId(requestBody.getOrderId());
        webhookRequestDto.setCustomerId(requestBody.getCustomerId());
        webhookRequestDto.setStatus("ORDER_CONFIRM");

        try {
            // webClient를 사용해 웹훅 전송
            webClientUtils.getWebClient(30000)
                    .post()
                    .uri(requestPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.ALL)
                    .body(BodyInserters.fromValue(webhookRequestDto))
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(i -> logger.info("<<< sendWebhook >>> 주문 승인 웹훅 전송 Success! webhookRequestDto={}", webhookRequestDto))
                    .doOnError(e -> logger.error("웹훅 전송 실패: {}", e.getMessage()))
                    .block();
        } catch (Exception e) {
            //웹훅 전송 중 오류 발생 시 처리
            return ResponseDto.builder().code("E002").message("웹훅 url 연결에 실패하였습니다.").build();
        }

        //성공적으로 웹훅 전송 후 성공 응답 반환
        return new ResponseDto("1", "SUCCESS");
    }
}
