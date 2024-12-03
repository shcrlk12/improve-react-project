package com.unison.monitoring.api.remark;

import com.unison.common.domain.Remark;
import com.unison.common.dto.RemarkDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RemarkService {
    List<Remark> getRemarks(UUID turbineUuid, LocalDateTime startDate, LocalDateTime endDate);

    List<Remark> getRemarksInADay(UUID turbineUuid, LocalDateTime startDate) throws Exception;
}
