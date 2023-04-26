package by.tux.helps.configuration;

import by.tux.helps.constants.Roles;
import by.tux.helps.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return login -> {
            User user = userRepo.findUserByLogin(login);
            if (user != null) return user;
            throw new UsernameNotFoundException("User ‘" + login + "’ not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                    .antMatchers("/requests/admin","/requests/admin/*","/requests/admin/**").hasRole(Roles.ADMIN)
                    .antMatchers("/users","/users/*","/users/**").hasRole(Roles.ADMIN)
                    .antMatchers("/requests/user","/requests/user/*","/requests/user/**").hasRole(Roles.USER)
                    .antMatchers("/", "/**").permitAll()
                    .antMatchers("/login", "/register").permitAll()
                    .antMatchers("/webjars/**").permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and().csrf().disable()
                .build();
    }
}