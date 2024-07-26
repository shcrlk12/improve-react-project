package com.unison.monitoring.api.jsonapiorg.request;

import com.unison.monitoring.api.jsonapiorg.Resource;
import lombok.Getter;
import lombok.Setter;

/*
 * @see https://jsonapi.org/
 */
@Setter
@Getter
public class ApiRequest<T> {
    private Resource<T> data; // Optional at least one
}
