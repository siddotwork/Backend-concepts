package com.app.civicfix.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY instead of AUTO for better MySQL support
    private Long id;

    private String name;
    private String type;

    @Lob
    @Column(name = "image_data", length = 1000000)
    private byte[] imageData;
}
