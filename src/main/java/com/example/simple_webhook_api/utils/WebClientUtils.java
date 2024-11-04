package com.example.simple_webhook_api.utils;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import reactor.netty.http.client.HttpClient;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class WebClientUtils {
    // 타임아웃 값을 기준으로 WebClient 객체를 저장하기 위한 Map
    protected final Map<Integer, WebClient> webClientMap = new HashMap<>();

    /**
     * 타임아웃 값을 기준으로 WebClient 객체를 가져오는 메서드
     * @param timeoutMillis 타임아웃 값 (밀리초)
     * @return WebClient 객체
     */
    public WebClient getWebClient(int timeoutMillis) {
        WebClient webClient = webClientMap.get(timeoutMillis);
        return webClient == null ? createWebClient(timeoutMillis).mutate().build() : webClient.mutate().build();
    }

    private WebClient createWebClient (int timeoutMillis) {
        int connectTimeoutMillis = Math.min(timeoutMillis, 5000); // 연결 타임아웃 5초로 제한
        int responseTimeoutMillis = Math.max(timeoutMillis, 30000); // 응답 타임아웃 최소 30초
        //HttpClient 설정
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis)
                .responseTimeout(Duration.ofMillis(responseTimeoutMillis))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(timeoutMillis, TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(timeoutMillis, TimeUnit.SECONDS)));

        //URI 인코딩 전략 설정
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        //WebClient 빌더 설정
        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        // WebClient 객체를 Map에 저장
        webClientMap.put(timeoutMillis, webClient);
        return webClient;
    }
}
