package com.lowyinstitute.asiapowerindex.repository;

import com.lowyinstitute.asiapowerindex.entity.CountryRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

//public interface CountryRankingRepository extends JpaRepository<CountryRanking, String> {
//    List<CountryRanking> findAllByOrderByTotalScoreDesc();
//}
public interface CountryRankingRepository extends JpaRepository<CountryRanking, String> {
    Optional<CountryRanking> findByCountryName(String countryName);

    List<CountryRanking> findAllByOrderByTotalScoreDesc();
}
