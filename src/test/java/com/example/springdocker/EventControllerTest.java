package com.example.springdocker;

import com.example.springdocker.data.EventRepository;
import com.example.springdocker.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@WebMvcTest
public class EventControllerTest {

    public static final String EVENTS = "/events";

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event(1, "concert");
        Mockito.when(eventRepository.findAll()).thenReturn(Collections.singletonList(event));
    }

    @Test
    void whenGetRequestToEvents_thenCorrectResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(EVENTS))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].name").value("concert"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPostRequestToEventsAndValidEvent_thenCorrectResponse() throws Exception {
        Mockito.when(eventRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);

        mockMvc.perform(MockMvcRequestBuilders.post(EVENTS)
                .content(objectMapper.writeValueAsString(new Event(null, "concert")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, EVENTS + "/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void whenPostRequestToEventsAndInvalidEvent_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(EVENTS)
                .content(objectMapper.writeValueAsString(new Event()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("must not be empty"));
    }

    @Test
    public void whenPutRequestToEventsAndValidEvent_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(EVENTS)
                .content(objectMapper.writeValueAsString(new Event(1, "concert_changed")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("concert_changed"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
