package com.url.shortner.url_mapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

	Optional<UrlMapping> findByOriginalUrl(String originalUrl);

	Optional<UrlMapping> findByShortUrl(String shortUrl);

}
