package com.example.springdocker.collection;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class BusinessOne implements BusinessUnit {
    @Override
    public void apply() {
        System.out.println("BusinessOne.apply");
    }

    @Override
    public String toString() {
        return "BusinessOne{}";
    }
}
