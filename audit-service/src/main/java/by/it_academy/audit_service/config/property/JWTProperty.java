package by.it_academy.audit_service.config.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTProperty {

    private String secret;

    private String issuer;
}
