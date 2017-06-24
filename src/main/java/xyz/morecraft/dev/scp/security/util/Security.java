package xyz.morecraft.dev.scp.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import xyz.morecraft.dev.scp.dao.User;
import xyz.morecraft.dev.scp.security.UserDetailsWrapper;

import java.util.Optional;

/**
 * Provides security util.
 */
public class Security {

    private Security() {

    }

    public static User currentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(UserDetailsWrapper.class::cast)
                .map(UserDetailsWrapper::getUser)
                .orElse(null);
    }

}
