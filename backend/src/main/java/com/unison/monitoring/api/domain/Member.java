package com.unison.monitoring.api.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {
    String id;
    String pw;
    String role;
    String name;
}
