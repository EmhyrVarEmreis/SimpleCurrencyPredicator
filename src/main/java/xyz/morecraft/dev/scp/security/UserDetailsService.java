package xyz.morecraft.dev.scp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserDetailsServiceInternal userDetailsServiceInternal;

    @Autowired
    public UserDetailsService(UserDetailsServiceInternal userDetailsServiceInternal) {
        this.userDetailsServiceInternal = userDetailsServiceInternal;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        return userDetailsServiceInternal.loadUserByUsername(login);
    }

}
