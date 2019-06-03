package tk.tarajki.meme.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tk.tarajki.meme.exceptions.UserAuthException;
import tk.tarajki.meme.services.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider, @Lazy UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null) {
            DecodedJWT tokenPayload;

            try {
                tokenPayload = jwtTokenProvider.getTokenPayload(token);
            } catch (TokenExpiredException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired.");
                return;
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Something is wrong with Token:" + e);
                return;
            }


            String username = jwtTokenProvider.getUsernameFromTokenPayload(tokenPayload);
            LocalDateTime lastTokenRelease;

            try {
                lastTokenRelease = jwtTokenProvider.getLastTokenReleaseFromTokenPayload(tokenPayload);
            } catch (UserAuthException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Something is wrong with Token." + e);
                return;
            }


            UserPrincipal principal;
            try {
                principal = (UserPrincipal) userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found.");
                return;
            }

            if (lastTokenRelease != principal.getUser().getLastTokenRelease()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token outdated.");
            }

            TokenBasedAuthentication  authentication = new TokenBasedAuthentication(principal, token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }
        if (!header.startsWith("Bearer ")) {
            return null;
        } else {
            return header.substring("Bearer ".length());
        }
    }

}
