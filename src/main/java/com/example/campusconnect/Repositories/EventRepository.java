package com.example.campusconnect.Repositories;


import com.example.campusconnect.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {}
