package com.unison.monitoring.api.jsonapi;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URI;

public class JsonApiOrgHttpHeaders extends HttpHeaders {

    public JsonApiOrgHttpHeaders(){
        setContentType(MediaType.valueOf("application/vnd.api+json"));

    }
    public JsonApiOrgHttpHeaders(String location){
        this();
        setLocation(URI.create(location));
    }

    // add Location
    public static JsonApiOrgHttpHeaders create(String location){
        return new JsonApiOrgHttpHeaders(location);
    }

    // add Location
    public static JsonApiOrgHttpHeaders create(HttpServletRequest httpServletRequest, String addLink){
        String baseUrl = httpServletRequest.getRequestURL().toString();

        if(addLink.charAt(0) == '/'){
            return new JsonApiOrgHttpHeaders(String.format("%s%s", baseUrl, addLink));
        }
        return new JsonApiOrgHttpHeaders(String.format("%s/%s", baseUrl, addLink));
    }
}
