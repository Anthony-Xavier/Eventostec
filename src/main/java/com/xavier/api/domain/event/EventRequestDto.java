package com.xavier.api.domain.event;

import org.springframework.web.multipart.MultipartFile;

public record EventRequestDto(String title, String description, Long data, String city, String state, Boolean remote, String eventUrl, MultipartFile image) {
}
