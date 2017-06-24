package xyz.morecraft.dev.scp.security.xauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDTO {

    private String token;
    private long expires;

}
