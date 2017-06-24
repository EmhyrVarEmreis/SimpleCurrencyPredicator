package xyz.morecraft.dev.scp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.morecraft.dev.scp.security.exception.UserBlockedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException,
            ServletException {

        log.debug("Pre-authenticated entry point called. Rejecting access");

        if (exception.getCause() instanceof UserBlockedException) {
            response.sendError(600, "User Blocked");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
        }

    }

}
