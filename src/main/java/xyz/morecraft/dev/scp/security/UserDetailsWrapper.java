package xyz.morecraft.dev.scp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.morecraft.dev.scp.dao.User;
import xyz.morecraft.dev.scp.dao.UserStatus;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsWrapper implements UserDetails {

    private User user;

    public UserDetailsWrapper(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getStatus().equals(UserStatus.BLC);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(UserStatus.ACT);
    }

}
