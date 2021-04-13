package pl.pracaInzynierska;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pracaInzynierska.model.Uzytkownik;
import pl.pracaInzynierska.repository.UzytkownikRepository;


/**
 *
 * @author m
 */
@Configuration
public class RoMa3Config extends WebSecurityConfigurerAdapter {

    @Autowired
    private UzytkownikRepository userRepo;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/login*").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and().formLogin().defaultSuccessUrl("/main", true)
                .and().logout().logoutUrl("/logout").deleteCookies("JSESSIONID")
                .and().exceptionHandling().accessDeniedPage("/403");
    }
   
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Uzytkownik user = userRepo.getUzytkownikByUsername(username);
                //return userRepo.getUzytkownikByUsername(username);
                UserDetails us = new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities());
                System.out.println("us" + us.getPassword());
                return us;
            }
        });
    }
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}



/*
https://github.com/MaciekBro/szafbook/blob/b33ee2119ee5b91d4a944bed5b744a81f5b41ceb/src/main/java/pl/namiekko/configuration/SecurityConfiguration.java#L57
https://github.com/MaciekBro/szafbook/blob/master/src/main/java/pl/namiekko/controllers/UserController.java
http://namiekko.pl/2016/08/31/spring-boot-autoryzacja-uzytkownikow-w-oparciu-o-baze-danych/
*/