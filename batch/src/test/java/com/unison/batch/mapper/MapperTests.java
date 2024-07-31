package com.unison.batch.mapper;

import com.unison.batch.jsonapiorg.Resource;
import com.unison.batch.model.ReportData;
import com.unison.batch.model.ReportDataDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTests {
    @Test
    public void testReportDataToResource(){
        //Given
        ReportData testData1 = new ReportData("2024-11-13 13:00:00", "1440", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        ReportData testData2 = new ReportData("2024-11-13 13:10:00", "1440", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        ReportData testData3 = new ReportData("2024-11-13 13:20:00", "1440", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");

        List<ReportData> testDataList = new ArrayList<>(Arrays.asList(testData1, testData2, testData3));

        //When
        List<Resource<ReportDataDto.Request>> resource = Mapper.reportDataToResource(testDataList);

        //Then
        assertEquals(resource.get(1).getId(), "2024-11-13 13:10:00");
        assertEquals(resource.get(1).getType(), "data");
        assertEquals(resource.get(1).getAttributes().getFullPerformance(), "1440");
        assertEquals(resource.size(), 3);
    }
}
