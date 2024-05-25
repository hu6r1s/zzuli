package com.hu6r1s.zzuli.service;

import com.hu6r1s.zzuli.dto.ShortUrlRequestDto;
import com.hu6r1s.zzuli.dto.ShortUrlResponseDto;
import com.hu6r1s.zzuli.entity.ShortenUrl;
import com.hu6r1s.zzuli.repository.ShortUrlRepository;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    @Override
    public ShortUrlResponseDto make(ShortUrlRequestDto requestDto) throws MalformedURLException {
        String originalUrl = requestDto.getOriginalUrl();
        urlValidator(originalUrl);
        ShortenUrl shortenUrl = new ShortenUrl(originalUrl);
        shortUrlRepository.save(shortenUrl);
        String shortUrl = idToHangul(shortenUrl.getId());
        return new ShortUrlResponseDto(shortUrl);
    }

    private void urlValidator(String url) throws MalformedURLException {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Invalid URL");
        }
    }

    private String idToHangul(Long id) {
        StringBuilder result = new StringBuilder();
        long base = 19 * 21 * 28;

        while (id > 0) {
            long remainder = (id - 1) % base;
            id = (id - 1) / base;

            result.append(convertToHangul(remainder));
        }

        log.info("shorten url : {}", result);
        return result.toString();
    }

    private String convertToHangul(long value) {
        int chosungCount = 19;
        int jungsungCount = 21;
        int jongsungCount = 28;

        int chosung = (int) (value % chosungCount);
        value /= chosungCount;

        int jungsung = (int) (value % jungsungCount);
        int jongsung = (int) (value / jungsungCount);

        char unicode = (char) (44032 + (chosung * jungsungCount * jongsungCount) + (jungsung * jongsungCount) + jongsung);
        return Character.toString(unicode);
    }
}
