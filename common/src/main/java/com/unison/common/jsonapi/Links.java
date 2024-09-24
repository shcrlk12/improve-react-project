package com.unison.common.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import jakarta.servlet.http.HttpServletRequest;

@Builder
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Null 값이 포함되지 않도록 설정
public class Links {
    private String self;
    private String related;  // Optional
    private String first;  // Optional
    private String last;  // Optional
    private String prev;  // Optional
    private String next;  // Optional

    public Links(String self) {
        this.self = self;
    }

    public static Links create(String link){
        return new Links(link);
    }

    public static Links create(HttpServletRequest httpServletRequest){
        String baseUrl = httpServletRequest.getRequestURL().toString();

        return new Links(baseUrl);
    }

    public static Links create(HttpServletRequest httpServletRequest, String addLink){
        String baseUrl = httpServletRequest.getRequestURL().toString();

        if(addLink.charAt(0) == '/'){
            return new Links(String.format("%s%s", baseUrl, addLink));
        }
        return new Links(String.format("%s/%s", baseUrl, addLink));
    }
}