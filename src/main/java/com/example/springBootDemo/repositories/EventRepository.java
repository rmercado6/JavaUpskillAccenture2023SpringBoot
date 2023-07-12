package com.example.springBootDemo.repositories;

import com.example.springBootDemo.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {}
