package com.example.springBootDemo.controllers;

import com.example.springBootDemo.models.Event;
import com.example.springBootDemo.models.input.EventCreateRequest;
import com.example.springBootDemo.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping("")
    public ResponseEntity<Iterable<Event>> getAllEvents() {
        Iterable<Event> events = eventsService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(eventsService.getEventById(Long.valueOf(id)), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found.");
        }
    }

    @PostMapping("")
    public ResponseEntity<Event> createEvent(@RequestBody EventCreateRequest eventCreateRequest) {
        Event event = eventsService.createNewEvent(eventCreateRequest.getTimeString());
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEventById(
            @PathVariable("id") String id,
            @RequestBody EventCreateRequest eventCreateRequest
    ) {
        try {
            return new ResponseEntity<>(
                    eventsService.updateEventById(Long.valueOf(id), eventCreateRequest.getTimeString()),
                    HttpStatus.OK
            );
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEventById(@PathVariable("id") String id) {
        try {
            eventsService.getEventById(Long.valueOf(id));
            eventsService.deleteEventById(Long.valueOf(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found.");
        }
    }
    }
