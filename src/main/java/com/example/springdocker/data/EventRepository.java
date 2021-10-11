package com.example.springdocker.data;


import com.example.springdocker.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Collection<Event> findByName(String name);

}
