package com.xavier.api.domain.event;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record EventRequestDto(String title, String description, Long data, String city, Boolean remote, String eventUrl, MultipartFile image) {
}
