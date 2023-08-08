package by.it_academy.task_service.utils;

import by.it_academy.task_manager_common.dto.UserDetailsImpl;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.task_manager_common.exceptions.CommonErrorException;
import by.it_academy.task_manager_common.exceptions.common.ExpiredTokenException;
import by.it_academy.task_manager_common.exceptions.common.NotValidTokenException;
import by.it_academy.task_manager_common.exceptions.common.NotValidTokenSignatureException;
import by.it_academy.task_service.config.property.JWTProperty;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
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
        } catch (Exception ex) {
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
            throw new NotValidTokenSignatureException(List.of(new ErrorResponse(ErrorType.ERROR, "Token signature is not valid. Try to log in again")));
        } catch (ExpiredJwtException ex) {
            throw new ExpiredTokenException(List.of(new ErrorResponse(ErrorType.ERROR, "Token is expired. Try to log in again")));
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
            throw new NotValidTokenException(List.of(new ErrorResponse(ErrorType.ERROR, "Token is not valid. Try to log in again")));
        }
    }
}
