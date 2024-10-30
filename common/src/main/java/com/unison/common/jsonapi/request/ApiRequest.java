package com.unison.common.jsonapi.request;

import com.unison.common.jsonapi.Resource;
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
