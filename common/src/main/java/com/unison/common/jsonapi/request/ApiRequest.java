package com.unison.common.jsonapi.request;

import com.unison.common.jsonapi.Resource;
import lombok.*;

/*
 * @see https://jsonapi.org/
 */
@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest<T> {
    private Resource<T> data; // Optional at least one
}
