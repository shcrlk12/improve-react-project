package com.unison.monitoring.member.dto;

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
