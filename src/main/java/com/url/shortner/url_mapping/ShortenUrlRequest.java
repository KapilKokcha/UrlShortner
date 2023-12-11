package com.url.shortner.url_mapping;

public class ShortenUrlRequest {
	private String originalUrl;
	private Long expiryTimeInHour;

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public Long getExpiryTimeInHour() {
		return expiryTimeInHour;
	}

	public void setExpiryTimeInHour(Long expiryTimeInHour) {
		this.expiryTimeInHour = expiryTimeInHour;
	}

}