package com.rentify.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
  private static final int TOKEN_VALIDITY = 3600 * 100;
  private String secretKey = "";

  public JwtUtil() {
    try {
      KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
      SecretKey sk = keyGen.generateKey();
      secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private SecretKey getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String getUserEmailFromToken(String token) {
    String withoutGmail = getClaimFromToken(token, Claims::getSubject);
    return withoutGmail + "@gmail.com";
  }

  private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String userEmail = getUserEmailFromToken(token);
//    System.out.println(userEmail + ": This is from JwtUtil ValidateToken line:55");
//    System.out.println("Not is TokenExpired: " + !isTokenExpired(token) + " line:56");
//    System.out.println("User Details getUserName() before: " + userDetails.getUsername() + " line:57");
//    System.out.println("User Emails.equals(userDetails.getUserName()): " + userEmail.equals(userDetails.getUsername()) + " line:58");
    String userDetailsExtractAndModifiedUserName = userDetails.getUsername() + "@gmail.com";
//    System.out.println("User Details getUserName() After: " + userDetailsExtractAndModifiedUserName + " line:60");
//    System.out.println("User Emails.equals(userDetails.getUserName()): " + userEmail.equals(userDetailsExtractAndModifiedUserName) + " line:61");
    return (userEmail.equals(userDetailsExtractAndModifiedUserName) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    final Date expDate = getExpirationDateFromToken(token);
    return expDate.before(new Date());
  }

  private Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
        .claims()
        .add(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
        .and()
        .signWith(getKey())
        .compact();
  }
}
