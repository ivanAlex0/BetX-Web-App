package betx.application.security;

import betx.application.filter.CustomAuthenticationFilter;
import betx.application.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("betx.authservice")
@ComponentScan("betx.apiservice")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "**").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "**").permitAll();
        /*httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/customer//save").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/customer/auth").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/customer/placeBet").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/test").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/getLeague").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/addressCountries").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/customer/checkBets").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/customer/withdraw").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/customer/deposit").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/verifier/save").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/verifier/changeStatus").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/verifier/auth").permitAll();*/

        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("ADMIN");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/customer/**").hasAnyAuthority("USER");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/customer/**").hasAnyAuthority("USER");

        httpSecurity.authorizeRequests().anyRequest().authenticated();

        httpSecurity.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
