package com.unison.batch.jsonapiorg.request;

import com.unison.batch.jsonapiorg.Resource;
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
