
/*
package pt.ipca.justintime.configs;


        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
/*

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
               // .antMatchers("/").hasAnyRole("USER", "ADMIN")
                .antMatchers("/login").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http
                .authorizeRequests()
                .antMatchers("/resources*/
/**
 * ").permitAll()
 * .anyRequest().permitAll();
 * }
 *
 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 * auth
 * .inMemoryAuthentication()
 * .withUser("user").password("password").roles("USER");
 * }
 * }
 */
