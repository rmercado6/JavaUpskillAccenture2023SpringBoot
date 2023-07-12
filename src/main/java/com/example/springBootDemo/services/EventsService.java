package com.example.springBootDemo.services;

import com.example.springBootDemo.models.Event;
import com.example.springBootDemo.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@Service
public class EventsService {

    @Autowired
    private EventRepository eventRepository;

    public Iterable<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) throws ChangeSetPersister.NotFoundException {
        return eventRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Event createNewEvent(String timeString){
        Event event = processTimeString(timeString);
        eventRepository.save(event);
        return event;
    }

    public Event updateEventById(Long id, String timeString) throws ChangeSetPersister.NotFoundException {
        Event event = eventRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Event updatedTimesEvent = processTimeString(timeString);
        event.setBeginning(updatedTimesEvent.getBeginning());
        event.setEnding(updatedTimesEvent.getEnding());
        eventRepository.save(event);
        return event;
    }

    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    private Event processTimeString(String timeString){
        String day = timeString.split(" ")[0];
        String beginning = timeString.split(" ")[1].split("-")[0];
        String ending = timeString.split(" ")[1].split("-")[1];

        String beginningDateTimeString = day + " " + beginning;
        String endingDateTimeString = day + " " + ending;

        DateTimeFormatter format = new DateTimeFormatterBuilder().appendPattern("E HH:mm")
                .parseDefaulting(ChronoField.YEAR, LocalDateTime.now().getYear())
                .parseDefaulting(
                        ChronoField.ALIGNED_WEEK_OF_YEAR,
                        LocalDateTime.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR)
                )
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        LocalDateTime beginningDateTime = LocalDateTime.parse(beginningDateTimeString, format);
        LocalDateTime endingDateTime = LocalDateTime.parse(endingDateTimeString, format);

        if (endingDateTime.isBefore(beginningDateTime)){
            throw new DateTimeException("Invalid dates.");
        }

        return new Event(beginningDateTime, endingDateTime);
    }
}
