package com.lowyinstitute.asiapowerindex.repository;

import com.lowyinstitute.asiapowerindex.entity.CountryMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryMeasureRepository extends JpaRepository<CountryMeasure, String> {
}
