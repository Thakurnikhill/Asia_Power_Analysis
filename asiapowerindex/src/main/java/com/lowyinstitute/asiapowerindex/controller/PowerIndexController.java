package com.lowyinstitute.asiapowerindex.controller;

import com.lowyinstitute.asiapowerindex.dto.CountryRankingResponse;
import com.lowyinstitute.asiapowerindex.dto.MeasureRankingResponse;
import com.lowyinstitute.asiapowerindex.entity.CountryMeasure;
import com.lowyinstitute.asiapowerindex.entity.CountryRanking;
import com.lowyinstitute.asiapowerindex.enums.MeasureName;
import com.lowyinstitute.asiapowerindex.repository.CountryMeasureRepository;
import com.lowyinstitute.asiapowerindex.service.PowerIndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/power-index")
public class PowerIndexController {

    private final PowerIndexService powerIndexService;
    private final CountryMeasureRepository measureRepository;

    public PowerIndexController(PowerIndexService powerIndexService,
                                CountryMeasureRepository measureRepository) {
        this.powerIndexService = powerIndexService;
        this.measureRepository = measureRepository;
    }

    @PostMapping("/measures")
    public ResponseEntity<CountryMeasure> submitCountryData(@RequestBody CountryMeasure measure) {
        CountryMeasure saved = measureRepository.save(measure);
        powerIndexService.updateRankings();
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/rankings/comprehensive")
    public ResponseEntity<List<CountryRanking>> getComprehensiveRankings() {
        return ResponseEntity.ok(powerIndexService.getComprehensiveRankings());
    }

    @GetMapping("/rankings/{measureName}")
    public ResponseEntity<List<MeasureRankingResponse>> getMeasureRankings(
            @PathVariable MeasureName measureName) {
        return ResponseEntity.ok(powerIndexService.getMeasureRankings(measureName));
    }

    @GetMapping("/countries/{countryName}")
    public ResponseEntity<CountryRanking> getCountryRanking(
            @PathVariable String countryName) {
        return ResponseEntity.ok(powerIndexService.getCountryRanking(countryName));
    }

}
