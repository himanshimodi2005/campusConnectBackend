package com.example.campusconnect.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostAndFound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Item name is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "LOST|FOUND", message = "Status must be 'LOST' or 'FOUND'")
    private String type; // LOST / FOUND

    @NotBlank(message = "Location is required")
    private String location;

    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

    private String reportedBy;
    private String photoUrl;
}