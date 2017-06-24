package xyz.morecraft.dev.scp.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prv_user")
@Getter
@Setter
@NoArgsConstructor
public class User extends PrivilegeObject {

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String email;
    private String firstName;
    private String lastName;

    private LocalDateTime created;

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + "\'," +
                "login='" + login + '\'' +
                '}';
    }

}
