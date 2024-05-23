package com.hu6r1s.zzuli.controller;

import com.hu6r1s.zzuli.dto.ShortUrlResponseDto;
import com.hu6r1s.zzuli.service.ShortUrlService;
import global.response.CommonResponse;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/hangul")
    public ResponseEntity<CommonResponse<ShortUrlResponseDto>> make(
            @RequestBody ShortUrlRequestDto requestDto
    ) throws MalformedURLException {
        ShortUrlResponseDto response = shortUrlService.make(requestDto);
        return CommonResponse.ok(response, HttpStatus.OK.getReasonPhrase());
    }
}
