package com.unison.monitoring.archiveddata.service;

import com.unison.monitoring.archiveddata.dto.ArchivedDataDto;

import java.util.List;
import java.util.UUID;

public interface ArchivedDataService {

    List<ArchivedDataDto> getAllArchivedData();

    ArchivedDataDto getArchivedDataByUUID(UUID turbineUuid);

}
