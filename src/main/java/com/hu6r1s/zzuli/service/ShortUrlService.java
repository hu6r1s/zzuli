package com.hu6r1s.zzuli.service;

import com.hu6r1s.zzuli.dto.request.ShortUrlRequestDto;
import com.hu6r1s.zzuli.dto.response.OriginalUrlResponseDto;
import com.hu6r1s.zzuli.dto.response.ShortUrlResponseDto;
import java.net.MalformedURLException;

public interface ShortUrlService {

  ShortUrlResponseDto make(ShortUrlRequestDto requestDto) throws MalformedURLException;

  OriginalUrlResponseDto redirect(String shortUrl);
}
