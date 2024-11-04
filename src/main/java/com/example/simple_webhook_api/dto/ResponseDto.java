package com.example.simple_webhook_api.dto;

import lombok.*;
import java.util.StringJoiner;

@Getter
@Setter
public class ResponseDto {
    private String code;
    private String message;


    @Builder
    public ResponseDto (String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String toString() {
        return new StringJoiner(",", ResponseDto.class.getSimpleName() + "[","]" )
                .add("code='" + code + "'")
                .add("message='" + message + "'")
                .toString();
    }

}
