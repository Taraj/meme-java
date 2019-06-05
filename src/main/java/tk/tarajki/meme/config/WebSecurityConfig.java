package tk.tarajki.meme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tk.tarajki.meme.models.RoleName;
import tk.tarajki.meme.security.JwtAuthorizationFilter;
import tk.tarajki.meme.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    private static final String[] SWAGGER = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };



    public WebSecurityConfig(
            @Lazy UserDetailsServiceImpl userDetailsService,
            @Lazy BCryptPasswordEncoder bCryptPasswordEncoder,
            JwtAuthorizationFilter jwtAuthorizationFilter
    ) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/error").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .antMatchers(SWAGGER).permitAll()

                .antMatchers("/api/v1/auth/**").permitAll()


                .antMatchers(HttpMethod.GET, "/api/v1/users/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/*/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/*/comments").permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/posts/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/posts/*/comments").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/posts/*/feedback").permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/comments/*").permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/tags/*/posts").permitAll()


                .antMatchers(HttpMethod.PUT, "/api/v1/posts/*").hasAuthority(RoleName.ROLE_ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/v1/posts/*").hasAuthority(RoleName.ROLE_ADMIN.name())

                .anyRequest().authenticated();


        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
