package com.example.campusconnect.controllers;


import com.example.campusconnect.entities.Event;
import com.example.campusconnect.services.EventService;
import jakarta.validation.Valid; // New Import
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    // Added @Valid
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event event) {
        return eventService.createEvent(event);
    }


    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        return eventService.getAllEvents();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    // NEW: Delete Controller Endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }
}