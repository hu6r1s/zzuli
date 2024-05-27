package com.hu6r1s.zzuli.entity.sequence;

import com.hu6r1s.zzuli.entity.ShortenUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShortUrlListener extends AbstractMongoEventListener<ShortenUrl> {

  private final SequenceGeneratorService generatorService;

  @Override
  public void onBeforeConvert(BeforeConvertEvent<ShortenUrl> event) {
    event.getSource().setId(generatorService.generateSequence(ShortenUrl.SEQUENCE_NAME));
  }
}
