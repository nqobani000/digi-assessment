package com.main.agency_booking.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class HttpResponse<T> {
    
    private T response;
    private String error;
    
    public HttpResponse(T body) {
        this.response = body;
    }
    
    public HttpResponse(String error) {
        this(null, error);
    }
}
