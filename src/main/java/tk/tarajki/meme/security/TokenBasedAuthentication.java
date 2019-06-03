package tk.tarajki.meme.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    private UserDetails principle;
    private String token;

    public TokenBasedAuthentication(UserDetails principle, String token) {
        super(principle.getAuthorities());
        this.principle = principle;
        this.token = token;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return principle;
    }
}
