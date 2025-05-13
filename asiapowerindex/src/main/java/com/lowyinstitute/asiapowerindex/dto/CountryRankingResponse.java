package com.lowyinstitute.asiapowerindex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryRankingResponse {
    private String countryName;
    private Double totalScore;
    private Integer rankPosition;
    private String trend;
}
