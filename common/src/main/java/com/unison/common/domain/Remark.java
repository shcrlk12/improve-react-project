package com.unison.common.domain;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Remark {
    private final Integer order;
    private final String title;
    private final String content;
}
