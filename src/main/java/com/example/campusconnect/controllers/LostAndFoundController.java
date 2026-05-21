package com.example.campusconnect.controllers;

import com.example.campusconnect.entities.LostAndFound;
import com.example.campusconnect.services.LostAndFoundService;
import jakarta.validation.Valid; // New Import
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lostfound")
@RequiredArgsConstructor
public class LostAndFoundController {

    private final LostAndFoundService lostAndFoundService;

    @PreAuthorize("hasRole('STUDENT') or hasRole('FACULTY')")
    @PostMapping
    // Added @Valid
    public LostAndFound reportItem(@Valid @RequestBody LostAndFound item) {
        return lostAndFoundService.reportItem(item);
    }

    @GetMapping
    public List<LostAndFound> getAllItems() {
        return lostAndFoundService.getAllItems();
    }

    // NEW: Get Detail by ID Endpoint
    @GetMapping("/{id}")
    public LostAndFound getItemById(@PathVariable Long id) {
        return lostAndFoundService.getItemById(id);
    }
}