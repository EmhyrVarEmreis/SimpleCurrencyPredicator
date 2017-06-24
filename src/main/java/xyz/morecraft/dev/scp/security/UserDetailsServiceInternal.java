package xyz.morecraft.dev.scp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.morecraft.dev.scp.dao.User;
import xyz.morecraft.dev.scp.dao.repository.UserRepository;

@Slf4j
@Component("userDetailsServiceInternal")
public class UserDetailsServiceInternal {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceInternal(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        User userFromDatabase = userRepository.findOneByLogin(login);
        checkUser(userFromDatabase, login);
        return new UserDetailsWrapper(userFromDatabase);
    }

    private void checkUser(User userFromDatabase, String login) {
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        }
    }

}
