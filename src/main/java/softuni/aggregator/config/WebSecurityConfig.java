package softuni.aggregator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationSuccessHandler successHandler;

    @Autowired
    public WebSecurityConfig(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
             .and()
                .csrf()
                .ignoringAntMatchers("/imports/**", "/exports/**", "/companies/delete/**", "/employees/delete/**",
                        "/admin/roles/update", "/admin/activate/**", "/admin/suspend/**")
                .csrfTokenRepository(csrfTokenRepository())
             .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/js/**", "/home").permitAll()
                .antMatchers("/register", "/login").anonymous()
                .antMatchers("/admin/**").hasAnyRole("ROOT_ADMIN", "ADMIN")
                .antMatchers("/imports/**").hasAnyRole("ROOT_ADMIN", "ADMIN", "MODERATOR")
                .anyRequest().authenticated()
             .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .successHandler(successHandler);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
