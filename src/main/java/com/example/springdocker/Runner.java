package com.example.springdocker;

import com.example.springdocker.collection.BusinessUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Runner implements ApplicationRunner {

    private final List<BusinessUnit> businessUnits;

    public Runner(List<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("{}", args);
        this.businessUnits.forEach(BusinessUnit::apply);
    }
}
