package com.hu6r1s.zzuli.repository;

import com.hu6r1s.zzuli.entity.ShortenUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortenUrl, Long> {

}
