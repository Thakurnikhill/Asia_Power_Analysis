package com.lowyinstitute.asiapowerindex.config;

import com.lowyinstitute.asiapowerindex.enums.MeasureName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMeasureNameConverter implements Converter<String, MeasureName> {
    @Override
    public MeasureName convert(String source) {
        return MeasureName.valueOf(source.replace("-", "_").toUpperCase());
    }
}
