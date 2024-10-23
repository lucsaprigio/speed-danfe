package com.example.speed_danfe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity(name = "indice")
public class DanfeEntity {

    @Id
    @Column(name = "iten")
    private Integer id;

    @Lob
    @Column(name = "arquivo")
    private byte[] arquivo;
}
