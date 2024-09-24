package com.unison.common.jsonapi;

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
}
