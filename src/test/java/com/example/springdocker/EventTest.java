package com.example.springdocker;

import com.example.springdocker.model.Event;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void creation() {
        Event event = new Event(1, "event name");

        assertEquals(1, event.getId());
        assertEquals("event name", event.getName());

        MatcherAssert.assertThat(event.getId(), Matchers.equalTo(1));

        Assertions.assertThat(event.getId()).isEqualTo(1);
    }
}