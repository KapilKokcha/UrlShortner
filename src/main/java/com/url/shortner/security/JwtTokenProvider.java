package com.url.shortner.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private int jwtExpiration;
	
	 @Autowired
	 private UserDetailsService userDetailsService; 

	public String generateToken(Authentication authentication) {
	    org.springframework.security.core.userdetails.User principal =
	            (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

	    Date now = new Date();
	    Date expiryDate = new Date(now.getTime() + jwtExpiration);

	    return Jwts.builder()
	            .setSubject(principal.getUsername())
	            .setIssuedAt(new Date())
	            .setExpiration(expiryDate)
	            .signWith(SignatureAlgorithm.HS512, jwtSecret)
	            .compact();
	}


//	public Long getUserIdFromToken(String token) {
//		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
//
//		return Long.parseLong(claims.getSubject());
//	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			// Handle exception, e.g., log it
		}
		return false;
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " is 7 characters
        }
        return null;
	}
	
	public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        // Extract authorities/roles from claims
//        List<String> authorities = (List<String>) claims.get("authorities");
//
//        Collection<? extends GrantedAuthority> grantedAuthorities =
//                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        // Retrieve user details from the database
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Returning UsernamePasswordAuthenticationToken
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }


}
