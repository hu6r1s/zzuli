package com.hu6r1s.zzuli.repository;

import com.hu6r1s.zzuli.entity.ShortenUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortUrlRepository extends MongoRepository<ShortenUrl, Long> {

}
