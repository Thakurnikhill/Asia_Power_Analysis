package com.lowyinstitute.asiapowerindex.service;

import com.lowyinstitute.asiapowerindex.dto.CountryRankingResponse;
import com.lowyinstitute.asiapowerindex.dto.MeasureRankingResponse;
import com.lowyinstitute.asiapowerindex.entity.CountryMeasure;
import com.lowyinstitute.asiapowerindex.entity.CountryRanking;
import com.lowyinstitute.asiapowerindex.enums.MeasureName;
import com.lowyinstitute.asiapowerindex.exception.ResourceNotFoundException;
import com.lowyinstitute.asiapowerindex.repository.CountryMeasureRepository;
import com.lowyinstitute.asiapowerindex.repository.CountryRankingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PowerIndexService {

    private final CountryMeasureRepository measureRepository;
    private final CountryRankingRepository rankingRepository;

    public PowerIndexService(CountryMeasureRepository measureRepository,
                             CountryRankingRepository rankingRepository) {
        this.measureRepository = measureRepository;
        this.rankingRepository = rankingRepository;
    }

    @Transactional
    public void updateRankings() {
        List<CountryMeasure> measures = measureRepository.findAll();

        List<CountryRanking> rankings = measures.stream()
                .sorted(Comparator.comparingDouble(CountryMeasure::getTotalScore).reversed())
                .map(m -> {
                    CountryRanking cr = new CountryRanking();
                    cr.setCountryName(m.getCountryName());
                    cr.setTotalScore(m.getTotalScore());
                    return cr;
                })
                .collect(Collectors.toList());

        // Rank assignment with tie handling
        int rank = 1;
        double prevScore = -1;
        for(int i = 0; i < rankings.size(); i++) {
            CountryRanking current = rankings.get(i);
            if(current.getTotalScore() != prevScore) {
                rank = i + 1;
            }
            current.setRankPosition(rank);
            prevScore = current.getTotalScore();
        }

        rankingRepository.saveAll(rankings);
    }

    public List<CountryRanking> getComprehensiveRankings() {
        return rankingRepository.findAllByOrderByTotalScoreDesc();
    }

    public List<MeasureRankingResponse> getMeasureRankings(MeasureName measureName) {
        List<CountryMeasure> measures = measureRepository.findAll();
        measures.forEach(this::calculateTotalScore);

        Comparator<CountryMeasure> comparator = getComparator(measureName);
        List<CountryMeasure> sorted = measures.stream()
                .sorted(comparator.reversed())
                .collect(Collectors.toList());

        return assignMeasureRanks(sorted, measureName);
    }

//    public List<CountryRankingResponse> getCountryRankings(String countryName) {
//        CountryMeasure measure = measureRepository.findById(countryName).orElseThrow();
//        calculateTotalScore(measure);
//
//        List<CountryRankingResponse> response = new ArrayList<>();
//        for (MeasureName measureName : MeasureName.values()) {
//            List<MeasureRankingResponse> rankings = getMeasureRankings(measureName);
//            Optional<MeasureRankingResponse> countryRank = rankings.stream()
//                    .filter(r -> r.getCountryName().equals(countryName))
//                    .findFirst();
//
//            countryRank.ifPresent(rank -> response.add(
//                    new CountryRankingResponse(
//                            rank.getCountryName(),
//                            rank.getScore(),
//                            rank.getRank(),
//                            getTrend(measureName, rank.getRank())
//                    )
//            ));
//        }
//        return response;
//    }

    public CountryRanking getCountryRanking(String countryName) {
        return rankingRepository.findByCountryName(countryName)
                .orElseThrow(() -> new ResourceNotFoundException("Country ranking not found"));
    }


    private void calculateTotalScore(CountryMeasure measure) {
        measure.setTotalScore(
                (measure.getEconomicCapability() * 0.175) +
                        (measure.getMilitaryCapability() * 0.175) +
                        (measure.getResilience() * 0.10) +
                        (measure.getFutureResources() * 0.10) +
                        (measure.getEconomicRelationships() * 0.15) +
                        (measure.getDefenceNetworks() * 0.10) +
                        (measure.getDiplomaticInfluence() * 0.10) +
                        (measure.getCulturalInfluence() * 0.10)
        );
    }

    private Comparator<CountryMeasure> getComparator(MeasureName measure) {
        return switch (measure) {
            case ECONOMIC_CAPABILITY -> Comparator.comparingDouble(CountryMeasure::getEconomicCapability);
            case MILITARY_CAPABILITY -> Comparator.comparingDouble(CountryMeasure::getMilitaryCapability);
            case RESILIENCE -> Comparator.comparingDouble(CountryMeasure::getResilience);
            case FUTURE_RESOURCES -> Comparator.comparingDouble(CountryMeasure::getFutureResources);
            case ECONOMIC_RELATIONSHIPS -> Comparator.comparingDouble(CountryMeasure::getEconomicRelationships);
            case DEFENCE_NETWORKS -> Comparator.comparingDouble(CountryMeasure::getDefenceNetworks);
            case DIPLOMATIC_INFLUENCE -> Comparator.comparingDouble(CountryMeasure::getDiplomaticInfluence);
            case CULTURAL_INFLUENCE -> Comparator.comparingDouble(CountryMeasure::getCulturalInfluence);
            case COMPREHENSIVE_POWER -> Comparator.comparingDouble(CountryMeasure::getTotalScore);
        };
    }

    private void assignRanks(List<CountryRanking> rankings) {
        int rank = 1;
        Double prevScore = -1D;
        for (int i = 0; i < rankings.size(); i++) {
            CountryRanking current = rankings.get(i);
            if (!current.getTotalScore().equals(prevScore)) {
                rank = i + 1;
            }
            current.setRankPosition(rank);
            prevScore = current.getTotalScore();
        }
    }

    private List<MeasureRankingResponse> assignMeasureRanks(List<CountryMeasure> measures, MeasureName measureName) {
        int rank = 1;
        Double prevScore = -1D;
        List<MeasureRankingResponse> results = new ArrayList<>();

        for (int i = 0; i < measures.size(); i++) {
            CountryMeasure current = measures.get(i);
            Double currentScore = getMeasureValue(current, measureName);

            if (!currentScore.equals(prevScore)) {
                rank = i + 1;
            }

            results.add(new MeasureRankingResponse(
                    current.getCountryName(),
                    measureName.toString(),
                    currentScore,
                    rank
            ));

            prevScore = currentScore;
        }
        return results;
    }

    private Double getMeasureValue(CountryMeasure measure, MeasureName name) {
        return switch (name) {
            case ECONOMIC_CAPABILITY -> measure.getEconomicCapability();
            case MILITARY_CAPABILITY -> measure.getMilitaryCapability();
            case RESILIENCE -> measure.getResilience();
            case FUTURE_RESOURCES -> measure.getFutureResources();
            case ECONOMIC_RELATIONSHIPS -> measure.getEconomicRelationships();
            case DEFENCE_NETWORKS -> measure.getDefenceNetworks();
            case DIPLOMATIC_INFLUENCE -> measure.getDiplomaticInfluence();
            case CULTURAL_INFLUENCE -> measure.getCulturalInfluence();
            case COMPREHENSIVE_POWER -> measure.getTotalScore();
        };
    }

    private String getTrend(MeasureName measureName, Integer currentRank) {
        return "UPWARD";
    }
}
