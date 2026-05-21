package com.example.campusconnect.services;


import com.example.campusconnect.Repositories.EventRepository;
import com.example.campusconnect.entities.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EventService {


    private final EventRepository eventRepository;


    public ResponseEntity<?> createEvent(Event event) {
        try {
            Event savedEvent = eventRepository.save(event);
            return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating event: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getAllEvents() {
        try {
            List<Event> events = eventRepository.findAll();
            if (events.isEmpty()) {
                return new ResponseEntity<>("No events found", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching events: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getEventById(Long id) {
        try {
            Optional<Event> event = eventRepository.findById(id);
            if (event.isPresent()) {
                return new ResponseEntity<>(event.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching event: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // NEW: Delete Event Logic
    public ResponseEntity<?> deleteEvent(Long id) {
        try {
            if (eventRepository.existsById(id)) {
                eventRepository.deleteById(id);
                return new ResponseEntity<>("Event deleted successfully", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting event: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}