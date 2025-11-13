package com.xavier.api.controller;

import com.xavier.api.domain.event.Event;
import com.xavier.api.domain.event.EventRequestDto;
import com.xavier.api.domain.event.EventResponseDto;
import com.xavier.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> createEvent(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("data") Long data,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam(value = "remote",required = false) Boolean remote,
            @RequestParam("eventUrl") String eventUrl,
            @RequestParam("image") MultipartFile image) {

        EventRequestDto eventRequestDto = new EventRequestDto(
                title, description, data, city, state,remote, eventUrl, image
        );

        Event createdEvent = eventService.createEvent(eventRequestDto);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getUpComingEvents(@RequestParam (defaultValue = "0")int page, @RequestParam (defaultValue = "10") int size){
        List<EventResponseDto> allEvents = this.eventService.getUpComingEvents(page, size);
        return ResponseEntity.ok(allEvents);
    }
}
