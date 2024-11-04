package com.example.simple_webhook_api.controller;

import com.example.simple_webhook_api.dto.*;
import com.example.simple_webhook_api.service.WebhookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@RequestMapping (value = "/webhook")
@RestController
public class WebhookProcessorController {

    private final WebhookService webhookService;

    WebhookProcessorController(
            WebhookService webhookService
    ) {
        this.webhookService = webhookService;
    }

    @PostMapping(value ="/request")
    public ResponseEntity<ResponseDto> requestWebhook(
            HttpServletRequest request
            , WebRequest webRequest
            , @RequestBody RequestDto requestBody
    ) {
        webRequest.setAttribute("requestBody", requestBody, RequestAttributes.SCOPE_REQUEST);
        return new ResponseEntity<ResponseDto>(webhookService.sendWebhook(requestBody), HttpStatus.OK);
    }
}
