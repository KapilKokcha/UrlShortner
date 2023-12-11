package com.url.shortner.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.url.shortner.url_mapping.ShortenUrlRequest;
import com.url.shortner.url_mapping.UrlMappingService;

@RestController
@RequestMapping("/api")
public class UrlShortenController {
	
	@Autowired
	private UrlMappingService urlMappingService;
	
	@PostMapping("/shortenUrl")
    public ResponseEntity<String> shortenUrl(@RequestBody ShortenUrlRequest shortenUrlRequest) {
		String shortUrl = urlMappingService.shortenUrl(shortenUrlRequest);
        return ResponseEntity.ok(shortUrl);
    }
	
	
	@GetMapping("/originalRedirect")
    public ResponseEntity<String> redirectToOriginalUrl(@RequestParam String shortUrl) {
        try {
        	String originalUrl = urlMappingService.retrieveOriginalUrl(shortUrl);
            return ResponseEntity.ok(originalUrl);
        }catch(ResponseStatusException e) {
        	throw e;
        }
    }
	
}
