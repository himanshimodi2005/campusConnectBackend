package com.example.campusconnect.Repositories;


import com.example.campusconnect.entities.LostAndFound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostAndFoundRepository extends JpaRepository<LostAndFound, Long> {}