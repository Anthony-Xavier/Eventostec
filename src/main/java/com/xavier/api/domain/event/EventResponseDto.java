package com.xavier.api.domain.event;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

public record EventResponseDto(UUID id, String title, String description, Date data, String city, String state, Boolean remote, String eventUrl, String image){
}
