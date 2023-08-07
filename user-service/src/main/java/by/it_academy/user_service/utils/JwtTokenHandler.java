package by.it_academy.user_service.utils;

import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.user_service.config.property.JWTProperty;
import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler {

    private static final String UUID_FIELD_NAME = "uuid";

    private static final String FIO_FIELD_NAME = "fio";

    private static final String ROLE_FIELD_NAME = "role";

    private final JWTProperty jwtProperty;

    public JwtTokenHandler(JWTProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    public String generateAccessToken(UserDetailsImpl userDetails) {
        return generateAccessToken(userDetails.getUuid(),
                userDetails.getUsername(),
                userDetails.getFio(),
                userDetails.getRole());
    }

    public String generateAccessToken(UUID uuid, String mail, String fio, UserRole role) {
        try {
            String compact = Jwts.builder()
                    .setSubject(mail)
                    .claim(UUID_FIELD_NAME, uuid)
                    .claim(FIO_FIELD_NAME, fio)
                    .claim(ROLE_FIELD_NAME, role)
                    .setIssuer(jwtProperty.getIssuer())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                    .signWith(SignatureAlgorithm.HS256, jwtProperty.getSecret())
                    .compact();
            return compact;
        }catch (Exception ex){
           throw new RuntimeException(ex.getCause());

        }
    }

    public String getMail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getUuid(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.get(UUID_FIELD_NAME, String.class);
    }

    public String getFio(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.get(FIO_FIELD_NAME, String.class);
    }

    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.get(ROLE_FIELD_NAME, String.class);
    }


    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public void validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token);
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
    }


}