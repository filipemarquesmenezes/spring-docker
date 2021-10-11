package com.example.springdocker;

import com.example.springdocker.model.Event;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventJpaTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Order(1)
    @Test
    void mapping() {
        Event event = testEntityManager.persistFlushFind(new Event(null, "event name"));
        Assertions.assertThat(event.getId()).isGreaterThan(0);
        Assertions.assertThat(event.getName()).isEqualTo("event name");
    }

//    @Order(2)
//    @Test
//    void mapping2() {
//        Event event = testEntityManager.persistFlushFind(new Event(null, "event name"));
//        Assertions.assertThat(event.getId()).isEqualTo(2);
//        Assertions.assertThat(event.getName()).isEqualTo("event name");
//        Assertions.assertThat(testEntityManager
//                .getEntityManager()
//                .createQuery("SELECT e FROM Event e", Event.class)
//                .getResultList()).hasSize(1);
//    }
}
