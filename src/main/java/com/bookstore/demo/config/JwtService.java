package com.bookstore.demo.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private static final String SECRET_KEY = "320be9cf05dc2118b6378f0995878bfd072d3f011615a4d4f4a3f91666c67982";

  public String generateToken(UserDetails userDetails) {
    return this.generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
    return Jwts
            .builder()
            .setClaims(extractClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000*24*60))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public String extractUsername(String jwtToken) {
    return this.extractClaims(jwtToken, Claims::getSubject);
  }

  public Date extractExpiration(String jwtToken) {
    return this.extractClaims(jwtToken, Claims::getExpiration);
  }

  public <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver){
    final Claims claims = this.extractAllClaims(jwtToken);
    return claimsResolver.apply(claims);
  }

  public Claims extractAllClaims(String jwtToken) {
    return Jwts
            .parserBuilder()
            .setSigningKey(this.getSigningKey())
            .build()
            .parseClaimsJws(jwtToken)
            .getBody();
  }

  private Key getSigningKey() {
    byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyByte);
  }

  public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
    final String username = this.extractUsername(jwtToken);
    return (username.equals(userDetails.getUsername())) && !this.isTokenExpired(jwtToken);
  }

  public boolean isTokenExpired(String jwtToke) {
    return this.extractExpiration(jwtToke).before(new Date());
  }
}
