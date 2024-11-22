package com.unison.batch.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum U151NtfTable {
    ZERO(0, 0.0,1.0,0.0),
    ONE(1, 3.34869,4.4077,-11.673),
    TWO(2, 3.43423,0.94728,0.21117),
    THREE(3, 3.98222,0.82101,0.714),
    FOUR(4, 4.61849,0.98283,-0.0333),
    FIVE(5, 5.14265,0.82058,0.80102),
    SIX(6, 5.71433, 1.00817,-0.2709),
    SEVEN(7, 6.25255, 0.83694, 0.79969),
    EIGHT(8 , 6.82378, 0.96605, -0.0813),
    NINE(9, 7.34927, 1.00458, -0.3644),
    TEN(10, 7.8171, 0.95615, 0.01411),
    ELEVEN(11, 8.33304, 0.86973, 0.73421),
    TWELVE(12, 8.90288, 0.71546, 2.10769),
    THIRTEEN(13, 9.61931, 0.85906, 0.7264),
    FOURTEEN(14, 10.2285, 1.11752, -1.9173),
    FIFTEEN(15, 10.6797, 0.9559, -0.19129),
    SIXTEEN(16, 11.2159, 0.95652, -0.19825),
    SEVENTEEN(17, 11.6855, 1.24764, -3.60014),
    EIGHTEEN(18, 12.1086, 0.8353, 1.39276),
    NINETEEN(19, 12.7036, 1.25767, -3.9728),
    TWENTY(20, 13.1026, 0.92464, 0.3907),
    TWENTYONE(21, 13.6523, 0.85072, 1.39997),
    TWENTYTWO(22, 14.2239, 1.02986, -1.14815),
    TWENTYTHREE(23, 14.7528, 0.81765, 1.98248),
    TWENTYFOUR(24, 15.2851, 0.94892, -0.02398),
    TWENTYFIVE(25, 15.809, 0.73797, 3.311);

    private final Integer order;
    private final Double windSpeed;
    private final Double slope;
    private final Double offset;

    public static U151NtfTable getNtfTable(Double windSpeed){
        U151NtfTable ntfValue = U151NtfTable.ZERO;

        for(U151NtfTable ntfTable : values()){
            if(windSpeed < ntfTable.windSpeed)
            {
                return ntfValue;
            }
            ntfValue = ntfTable;
        }
       return U151NtfTable.TWENTYFIVE;
    }
}
