package xyz.morecraft.dev.scp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

    private String login;
    private String password;

}
