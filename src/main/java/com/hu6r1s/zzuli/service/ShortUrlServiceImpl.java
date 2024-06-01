package com.hu6r1s.zzuli.service;

import com.hu6r1s.zzuli.dto.request.ShortUrlRequestDto;
import com.hu6r1s.zzuli.dto.response.OriginalUrlResponseDto;
import com.hu6r1s.zzuli.dto.response.ShortUrlResponseDto;
import com.hu6r1s.zzuli.entity.ShortenUrl;
import com.hu6r1s.zzuli.repository.ShortUrlRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

  private final ShortUrlRepository shortUrlRepository;

  private static final int CHOSUNG_COUNT = 19;
  private static final int JUNGSUNG_COUNT = 21;
  private static final int JONGSUNG_COUNT = 28;
  private static final long BASE = CHOSUNG_COUNT * JUNGSUNG_COUNT * JONGSUNG_COUNT;

  @Override
  public ShortUrlResponseDto make(ShortUrlRequestDto requestDto) throws MalformedURLException {
    String originalUrl = requestDto.getOriginalUrl();
    urlValidator(originalUrl);
    ShortenUrl shortenUrl = new ShortenUrl(originalUrl);
    shortUrlRepository.save(shortenUrl);
    String shortUrl = idToHangul(shortenUrl.getId());
    return new ShortUrlResponseDto(shortUrl);
  }

  @Override
  public OriginalUrlResponseDto redirect(String shortUrl) {
    long id = hangulToId(shortUrl);

    ShortenUrl shortenUrl = shortUrlRepository.findById(id)
        .orElseThrow(NullPointerException::new);

    log.info("Redirect URL ID : {}", id);
    log.info("Redirect URL : {}", shortenUrl.getOriginalUrl());

    return new OriginalUrlResponseDto(shortenUrl.getOriginalUrl());
  }

  private void urlValidator(String url) throws MalformedURLException {
    try {
      new URL(url);
    } catch (MalformedURLException e) {
      log.error("Malformed URL : {}", e);
      throw e;
    }
  }

  private String idToHangul(Long id) {
    StringBuilder result = new StringBuilder();

    while (id > 0) {
      long remainder = (id - 1) % BASE;
      id = (id - 1) / BASE;

      result.append(convertToHangul(remainder));
    }

    log.info("Shorted URL : {}", result);
    return result.toString();
  }

  private String convertToHangul(long value) {

    int chosung = (int) (value % CHOSUNG_COUNT);
    value /= CHOSUNG_COUNT;

    int jungsung = (int) (value % JUNGSUNG_COUNT);
    int jongsung = (int) (value / JUNGSUNG_COUNT);

    char unicode = (char) (44032 + (chosung * JUNGSUNG_COUNT * JONGSUNG_COUNT) + (jungsung
        * JONGSUNG_COUNT) + jongsung);
    return Character.toString(unicode);
  }

  private long hangulToId(String shortUrl) {
    long id = 0;

    for (int i = 0; i < shortUrl.length(); i++) {
      char url = shortUrl.charAt(i);
      String jamo = Normalizer.normalize(Character.toString(url), Form.NFD);

      int chosungIndex = jamo.charAt(0) - 0x1100;
      int jungsungIndex = jamo.charAt(1) - 0x1161;
      int jongsungIndex = (jamo.length() > 2) ? jamo.charAt(2) - 0x11A7 : 0;

      log.info("{}, {}, {}", chosungIndex, jungsungIndex, jongsungIndex);
      id += chosungIndex + (CHOSUNG_COUNT * jungsungIndex) + (CHOSUNG_COUNT * JUNGSUNG_COUNT) * jongsungIndex + BASE * i;
    }

    return id + 1;
  }
}
