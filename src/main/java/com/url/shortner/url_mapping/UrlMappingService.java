package com.url.shortner.url_mapping;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.ZoneIdConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UrlMappingService {

	@Autowired
	private UrlMappingRepository urlMappingRepository;

	public String shortenUrl(ShortenUrlRequest shortenUrlRequest) {

		ZonedDateTime expiryTime = calculateExpiryTime(Duration.ofHours(shortenUrlRequest.getExpiryTimeInHour()), ZoneId.of("Asia/Kolkata"));

		// Check if a URL with the same original URL already exists
		Optional<UrlMapping> existingUrlMapping = urlMappingRepository.findByOriginalUrl(shortenUrlRequest.getOriginalUrl());

		if (existingUrlMapping.isPresent()) {
			// If the URL exists, update its expiry time to the latest expiry
			existingUrlMapping.get().setExpiryTime(expiryTime);
			urlMappingRepository.save(existingUrlMapping.get());
			return existingUrlMapping.get().getShortUrl();
		} else {
			// If the URL doesn't exist, create a new entry
			// Implement URL shortening logic (e.g., generate a unique short code)
			String shortCode = generateShortCode();
			UrlMapping urlMapping = new UrlMapping();
			urlMapping.setOriginalUrl(shortenUrlRequest.getOriginalUrl());
			urlMapping.setShortUrl(shortCode);
			urlMapping.setExpiryTime(expiryTime);
			urlMappingRepository.save(urlMapping);
			return shortCode;

		}

	}
	
	public String retrieveOriginalUrl(String shortUrl) {
	    Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findByShortUrl(shortUrl);

	    if (urlMappingOptional.isPresent()) {
	        UrlMapping urlMapping = urlMappingOptional.get();

	        // Check if the URL is expired
	        if (urlMapping.getExpiryTime().isAfter(ZonedDateTime.now())) {
	            // URL is still valid, return the original URL
	            return urlMapping.getOriginalUrl();
	        } else {
	            // URL is expired, delete it from the database
	            urlMappingRepository.delete(urlMapping);
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL does not exist or has expired.");
	        }
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL does not exist.");
	    }
	}

	private ZonedDateTime calculateExpiryTime(Duration expiryDuration, ZoneId zoneId) {
		// Calculate the expiry time based on the specified duration and timezone
		return ZonedDateTime.now(zoneId).plus(expiryDuration);
	}

	private static String generateShortCode() {
		// Generating a unique identifier using UUID
		String uniqueId = UUID.randomUUID().toString();

		// Encoding the unique identifier using Base64
		String encodedId = Base64.getUrlEncoder().withoutPadding().encodeToString(uniqueId.getBytes());

		// Extracting only alphanumeric characters from the encoded string
		String alphanumericCode = encodedId.replaceAll("[^a-zA-Z0-9]", "");

		// Trimming the code to a specific length (e.g., 8 characters)
		int desiredLength = 8;
		if (alphanumericCode.length() > desiredLength) {
			alphanumericCode = alphanumericCode.substring(0, desiredLength);
		}

		return alphanumericCode;
	}
}
