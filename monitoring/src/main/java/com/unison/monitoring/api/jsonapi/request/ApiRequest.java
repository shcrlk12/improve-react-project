package com.unison.monitoring.api.jsonapi.request;

import com.unison.monitoring.api.jsonapi.Resource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * @see https://jsonapi.org/
 */
@Setter
@Builder
@Getter
public class ApiRequest<T> {
    private Resource<T> data; // Optional at least one
}
