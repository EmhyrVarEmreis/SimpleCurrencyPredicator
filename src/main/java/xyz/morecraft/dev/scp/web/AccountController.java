package xyz.morecraft.dev.scp.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xyz.morecraft.dev.scp.dao.User;
import xyz.morecraft.dev.scp.dto.AuthenticationDTO;
import xyz.morecraft.dev.scp.dto.LoginDTO;
import xyz.morecraft.dev.scp.dto.UserDTO;
import xyz.morecraft.dev.scp.security.UserDetailsServiceInternal;
import xyz.morecraft.dev.scp.security.UserDetailsWrapper;
import xyz.morecraft.dev.scp.security.util.Security;
import xyz.morecraft.dev.scp.security.xauth.TokenProvider;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceInternal userDetailsService;
    private final TokenProvider tokenProvider;

    @Autowired
    public AccountController(AuthenticationManager authenticationManager, UserDetailsServiceInternal userDetailsService, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getAccount() {
        User user = Security.currentUser();
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                null,
                user.getStatus().toString(),
                user.getEmail()
        );
    }

    @PostMapping(value = "/authenticate")
    public AuthenticationDTO authorize(@RequestBody LoginDTO credentials) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsWrapper details = (UserDetailsWrapper) this.userDetailsService.loadUserByUsername(credentials.getLogin());
        return new AuthenticationDTO(tokenProvider.createToken(details), null);
    }

}
