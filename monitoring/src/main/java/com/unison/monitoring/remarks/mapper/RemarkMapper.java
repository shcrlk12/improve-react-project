package com.unison.monitoring.remarks.mapper;

import com.unison.common.jsonapi.Resource;
import com.unison.common.jsonapi.request.ApiRequests;
import com.unison.monitoring.remarks.dto.RemarkDto;
import com.unison.monitoring.remarks.entity.RemarkDataEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RemarkMapper {
    public static List<RemarkDto> toDto(List<RemarkDataEntity> remarkEntities){
        List<RemarkDto> result = null;

        try{
            result = remarkEntities.stream()
                    .sorted(Comparator.comparing(o -> o.getRemarkMetaEntity().getOrderId()))
                    .map(remarkDataEntity -> RemarkDto.builder()
                            .title(remarkDataEntity.getRemarkMetaEntity().getTitle())
                            .order(remarkDataEntity.getRemarkMetaEntity().getOrderId())
                            .content(remarkDataEntity.getDescription())
                            .turbineUuid(remarkDataEntity.getUuid())
                            .build())
                    .collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }

    public static List<Resource<com.unison.common.dto.RemarkDto.Response>> toRemarkResponseDtoList(ApiRequests<com.unison.common.dto.RemarkDto.Request> remarkDtoRequests){
        List<Resource<com.unison.common.dto.RemarkDto.Response>> result = null;

        try{
            result = remarkDtoRequests.getData().stream()
                    .map(remarkDtoRequest ->
                            Resource.<com.unison.common.dto.RemarkDto.Response>builder()
                                    .id(remarkDtoRequest.getId())
                                    .type(remarkDtoRequest.getType())
                                    .attributes(new com.unison.common.dto.RemarkDto.Response(remarkDtoRequest.getAttributes().getTitle(), remarkDtoRequest.getAttributes().getContent(), remarkDtoRequest.getAttributes().getTurbineUuid()))
                                    .build()
                    )
                    .toList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return Objects.requireNonNull(result);
    }

}
