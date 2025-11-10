package com.xavier.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.xavier.api.domain.event.Event;
import com.xavier.api.domain.event.EventRequestDto;
import com.xavier.api.domain.event.EventResponseDto;
import com.xavier.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventRequestDto eventRequestDto) {
        String imgUrl = null;

        MultipartFile image = eventRequestDto.image();
        if (image != null && !image.isEmpty()) {
            imgUrl = uploadImage(image);
        }

        Event newEvent = new Event();
        newEvent.setTitle(eventRequestDto.title());
        newEvent.setDescription(eventRequestDto.description());
        newEvent.setEventUrl(eventRequestDto.eventUrl());
        newEvent.setData(new Date(eventRequestDto.data()));
        newEvent.setImgUrl(imgUrl);
        newEvent.setRemote(eventRequestDto.remote());

        return eventRepository.save(newEvent);
    }

    public List<EventResponseDto> getEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventRepository.findAll(pageable);

        return eventPage
                .map(event -> new EventResponseDto(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getData(),
                       "",
                       "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl()
                ))
                .toList();
    }

    private String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            amazonS3Client.putObject(bucketName, fileName, file);
            return amazonS3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da imagem para o S3", e);
        } finally {
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("upload-", Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }
}
