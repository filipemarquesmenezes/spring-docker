package com.example.springdocker;

import com.example.springdocker.data.EventRepository;
import com.example.springdocker.model.Event;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;

@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void findByEventName() {
        eventRepository.save(new Event(1, "concert"));
        Collection<Event> events = eventRepository.findByName("concert");

        Assertions.assertThat(events.size()).isGreaterThan(0);
        Assertions.assertThat(events.stream().findFirst().get().getId()).isGreaterThan(0);
        Assertions.assertThat(events.stream().findFirst().get().getName()).isEqualTo("concert");
    }
}
