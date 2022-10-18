package recipes.webconfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passEnc;
    private final UserDetailsService details;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/actuator/shutdown")
                .permitAll()

                .mvcMatchers(/*HttpMethod.POST,*/ "/api/register")
                .permitAll()

                .anyRequest().authenticated()

                .and()
                .httpBasic()

                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(details)
                .passwordEncoder(passEnc);
        auth
                .inMemoryAuthentication()
                .withUser("DuckDeduct")
                .password(passEnc.encode("password"))
                .roles()
                .and()
                .passwordEncoder(passEnc);
    }
}
