package com.example.springdocker.api;

import com.example.springdocker.data.EventRepository;
import com.example.springdocker.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping(value = "/events")
    public List<Event> get() {
        log.info("Get events");
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    public ResponseEntity<Void> post(@Valid @RequestBody Event event) {
        log.info("Create event {}", event.toString());
        event = eventRepository.save(event);
        return ResponseEntity.created(URI.create("/events/" + event.getId())).build();
    }

    @PutMapping("/events")
    public ResponseEntity<Event> put(@Valid @RequestBody Event event) {
        log.info("Update event {}", event.toString());
        eventRepository.save(event);
        return ResponseEntity.ok(event);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
