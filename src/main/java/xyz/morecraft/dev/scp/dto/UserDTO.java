package xyz.morecraft.dev.scp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private String status;
    private String email;

}
