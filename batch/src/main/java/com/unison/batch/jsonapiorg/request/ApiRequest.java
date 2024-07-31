package com.unison.batch.jsonapiorg.request;

import com.unison.batch.jsonapiorg.Resource;
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
