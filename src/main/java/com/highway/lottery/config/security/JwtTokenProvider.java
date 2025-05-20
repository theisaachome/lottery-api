package com.highway.lottery.config.security;
import com.highway.lottery.common.exception.APIException;
import com.highway.lottery.common.exception.ResourceNotFoundException;
import com.highway.lottery.modules.account.repo.AccountRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secrete}")
    private String secrete;
    @Value("${app.jwt-issuer}")
    private String issuer;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpiration;

    private final AccountRepo accountRepo;

    public JwtTokenProvider(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    // generate token
    public String generateToken(Authentication authentication) {
         var securityUser =(SecurityUser) authentication.getPrincipal();
         var account = securityUser.getAccount();
//         var account = accountRepo.findAccountByUsername(username)
//                 .orElseThrow(()->new ResourceNotFoundException("Bad Credential : NO User  found " + username));

         var currentDate = new Date();
         var expirationDate = new Date(currentDate.getTime() + jwtExpiration);
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountId", account.getId());

         String token = Jwts.builder()
                 .setClaims(claims)
                 .setSubject(account.getUsername())
                 .setIssuer(issuer)
                 .setIssuedAt(currentDate)
                 .setExpiration(expirationDate)
                 .signWith(key())
                 .compact();
        return token;
    }

    // get username from jwtToken
    public String getUsernameFromToken(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(jwtToken).getBody();
        return claims.getSubject();
    }
    public Long getUserId(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token).getBody();
        return claims.get("accountId",Long.class);
    }
    // validate jwtToken
    public boolean validateToken(String jwtToken){
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(jwtToken).getBody();
            return !claims.getExpiration().before(new Date());
        }
         catch (ExpiredJwtException e) {
            throw  new APIException(HttpStatus.BAD_REQUEST,"Token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw  new   APIException(HttpStatus.BAD_REQUEST,"Unsupported JWT: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw  new   APIException(HttpStatus.BAD_REQUEST,"Illegal argument: " + e.getMessage());
        }
        return false;
    }


    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secrete));
    }
}
