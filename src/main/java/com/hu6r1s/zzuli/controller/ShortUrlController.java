package com.hu6r1s.zzuli.controller;

import com.hu6r1s.zzuli.dto.request.ShortUrlRequestDto;
import com.hu6r1s.zzuli.dto.response.OriginalUrlResponseDto;
import com.hu6r1s.zzuli.dto.response.ShortUrlResponseDto;
import com.hu6r1s.zzuli.service.ShortUrlService;
import global.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/{shortUrl}")
  public ResponseEntity<CommonResponse<Void>> redirect(
      @PathVariable String shortUrl
  ) {
    OriginalUrlResponseDto response = shortUrlService.redirect(shortUrl);
//    return CommonResponse.redirection(response.getOriginalUrl(),
//        HttpStatus.MOVED_PERMANENTLY.getReasonPhrase());
    return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
        .header("Location", response.getOriginalUrl())
        .body(null);
  }
}
