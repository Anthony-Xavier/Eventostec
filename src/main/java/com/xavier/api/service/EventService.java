package com.xavier.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.xavier.api.domain.event.Event;
import com.xavier.api.domain.event.EventRequestDto;
import com.xavier.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Autowired
    EventRepository eventRepository;

    public Event createEvent(EventRequestDto eventRequestDto) {
        String imgUrl = null;

        if(eventRequestDto.image() != null) {
            imgUrl = this.uploadImage(eventRequestDto.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(eventRequestDto.title());
        newEvent.setDescription(eventRequestDto.description());
        newEvent.setEventUrl(eventRequestDto.eventUrl());
        newEvent.setData(new Date(eventRequestDto.data()));
        newEvent.setImgUrl(imgUrl);
        newEvent.setRemote(eventRequestDto.remote());

        eventRepository.save(newEvent);

        return newEvent;
    }

    private String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try {
            File file = this.convertMultiPartToFile(multipartFile);
            amazonS3Client.putObject(bucketName,fileName,file);
            file.delete();
            return amazonS3Client.getUrl(bucketName,fileName).toString();
        }catch (Exception e){
            System.out.println("Erro ao subir arquivo");
            return "----ERRO-------";
        }
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convFive = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFive);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFive;
    }
}
