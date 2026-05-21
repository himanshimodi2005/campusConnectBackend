package com.example.campusconnect.services;


import com.example.campusconnect.Repositories.LostAndFoundRepository;
import com.example.campusconnect.entities.LostAndFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.util.List;


@Service
@RequiredArgsConstructor
public class LostAndFoundService {


    private final LostAndFoundRepository lostAndFoundRepository;


    public LostAndFound reportItem(LostAndFound item) {
        return lostAndFoundRepository.save(item);
    }


    public List<LostAndFound> getAllItems() {
        return lostAndFoundRepository.findAll();
    }

    // NEW: Get Item By ID Detail Logic
    public LostAndFound getItemById(Long id) {
        return lostAndFoundRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lost and Found item not found with ID: " + id));
    }
}