package com.example.springdocker.collection;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(-1)
@Component
public class BusinessThree implements BusinessUnit {
    @Override
    public void apply() {
        System.out.println("BusinessThree.apply");
    }
}
