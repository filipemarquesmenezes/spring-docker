package com.example.springdocker.collection;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class BusinessTwo implements BusinessUnit {
    @Override
    public void apply() {
        System.out.println("BusinessTwo.apply");
    }

    @Override
    public String toString() {
        return "BusinessTwo{}";
    }
}
