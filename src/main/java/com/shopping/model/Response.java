package com.shopping.model;

import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
@ToString
public class Response<T> {

    private T data;  // Response Data
    private String message; //custom Message // Handled Exception
    private List<String> error; // Java Error Code
    private String errorDesc; // Java Error Description
    private String url; //HTTP Request URL - GET/POST
    private Long totalRecords;
    private String errorType;
}
