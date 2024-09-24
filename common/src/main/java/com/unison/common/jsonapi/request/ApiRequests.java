package com.unison.common.jsonapi.request;

import com.unison.common.jsonapi.Resource;
import lombok.*;

import java.util.List;

/*
 * @see https://jsonapi.org/
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiRequests<T> {
    private List<Resource<T>> data; // Optional at least one
}
