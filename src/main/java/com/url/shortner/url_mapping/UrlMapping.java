package com.url.shortner.url_mapping;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UrlMapping {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(unique = true, nullable = false)
    private String shortUrl;
    
    @Column(nullable = false)
    private ZonedDateTime expiryTime;

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public ZonedDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(ZonedDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}
    
    
    
}
