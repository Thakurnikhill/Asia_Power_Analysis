package com.lowyinstitute.asiapowerindex.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country_measures")
@Getter
@Setter
public class CountryMeasure {
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getEconomicCapability() {
        return economicCapability;
    }

    public void setEconomicCapability(Double economicCapability) {
        this.economicCapability = economicCapability;
    }

    public Double getMilitaryCapability() {
        return militaryCapability;
    }

    public void setMilitaryCapability(Double militaryCapability) {
        this.militaryCapability = militaryCapability;
    }

    public Double getResilience() {
        return resilience;
    }

    public void setResilience(Double resilience) {
        this.resilience = resilience;
    }

    public Double getFutureResources() {
        return futureResources;
    }

    public void setFutureResources(Double futureResources) {
        this.futureResources = futureResources;
    }

    public Double getEconomicRelationships() {
        return economicRelationships;
    }

    public void setEconomicRelationships(Double economicRelationships) {
        this.economicRelationships = economicRelationships;
    }

    public Double getDefenceNetworks() {
        return defenceNetworks;
    }

    public void setDefenceNetworks(Double defenceNetworks) {
        this.defenceNetworks = defenceNetworks;
    }

    public Double getDiplomaticInfluence() {
        return diplomaticInfluence;
    }

    public void setDiplomaticInfluence(Double diplomaticInfluence) {
        this.diplomaticInfluence = diplomaticInfluence;
    }

    public Double getCulturalInfluence() {
        return culturalInfluence;
    }

    public void setCulturalInfluence(Double culturalInfluence) {
        this.culturalInfluence = culturalInfluence;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    @Id
    private String countryName;
    private Double economicCapability;
    private Double militaryCapability;
    private Double resilience;
    private Double futureResources;
    private Double economicRelationships;
    private Double defenceNetworks;
    private Double diplomaticInfluence;
    private Double culturalInfluence;

    @Transient
    private Double totalScore;

    @PrePersist
    @PreUpdate
    public void calculateTotalScore() {
        this.totalScore = (economicCapability * 0.175) +
                (militaryCapability * 0.175) +
                (resilience * 0.10) +
                (futureResources * 0.10) +
                (economicRelationships * 0.15) +
                (defenceNetworks * 0.10) +
                (diplomaticInfluence * 0.10) +
                (culturalInfluence * 0.10);
    }
}
