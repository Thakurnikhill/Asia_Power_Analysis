package com.lowyinstitute.asiapowerindex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@AllArgsConstructor
public class MeasureRankingResponse {
    public MeasureRankingResponse(String countryName, String measureName, Double score, int rank) {
        this.countryName = countryName;
        this.measureName = measureName;
        this.score = score;
        this.rank = rank;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    private String countryName;
    private String measureName;
    private Double score;
    private Integer rank;

//    public MeasureRankingResponse() { }

}
