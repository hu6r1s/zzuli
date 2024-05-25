package com.hu6r1s.zzuli.service;

import com.hu6r1s.zzuli.dto.ShortUrlRequestDto;
import com.hu6r1s.zzuli.dto.ShortUrlResponseDto;
import java.net.MalformedURLException;

public interface ShortUrlService {

    ShortUrlResponseDto make(ShortUrlRequestDto requestDto) throws MalformedURLException;
}
