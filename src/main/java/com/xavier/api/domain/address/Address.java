package com.xavier.api.domain.address;

import com.xavier.api.domain.event.Event;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Table(name = "address")
@Entity
public class Address {
    @Id
    @GeneratedValue
    private UUID id;

    private String city;

    private String uf;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
