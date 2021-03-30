package nessolution.security;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import nessolution.security.jwt.JwtAuthenticationEntryPoint;
import nessolution.security.jwt.JwtAuthenticationFilter;
import nessolution.security.jwt.JwtUserDetailsService;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = getLogger(SecurityConfig.class);

    private final String TOEKN_HEADER = "auth-header";

    private static final String AUTHENTICATION_PATH = "/auth";
    private static final String HOME_ENTRY_POINT = "/";
    private static final String CREATE_USER_ENTRY_POINT = "/createUser";
    private static final String LOGIN_ENTRY_POINT = "/loginForm";
    private static final String LOGIN_USER_ENTRY_POINT = "/loginUser";
    private static final String TOKEN_ENTRY_POINT = "/token";
    private static final String ERROR_ENTRY_POINT = "/error";
    private static final String ROOT_ENTRY_POINT = "/**";
    private static final String HELLO_ENTRY_POINT = "/hello";
    private static final String VUEJS_ENTRY_POINT = "https://unpkg.com/vue/dist/vue.js";
    private static final String AXIOS_ENTRY_POINT = "https://unpkg.com/axios@0.19.0/dist/axios.min.js";
    private static final String UPLOAD_ENTRY_POINT = "/productUpload";
    private static final String CHANGE_PASSWORD = "/changePassword";
    private static final String FIND_EMAIL = "/findEmail";
    private static final String FIND_EMAIL_FORM = "/findEmailForm";
    private static final String FIND_PASSWORD = "/findPassword";
    private static final String FIND_PASSWORD_FORM = "/findPasswordForm";
    private String tokenHeader = "Authorization";

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Bean
//    public AntPathRequestMatcher antPathRequestMatcher() {
//        return new AntPathRequestMatcher(LOGIN_USER_ENTRY_POINT, HttpMethod.POST.name());
//    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // - (3)
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");  // TODO: lock down before deploying
        config.addAllowedHeader("*");
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://locahost:7080"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()

                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/member/**", "/", "/page/**","/images/**","/find/**",
                        "/product/selectById/**","/product/delete/**",
                        "/customLogout",VUEJS_ENTRY_POINT, AXIOS_ENTRY_POINT).permitAll()
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                ;

        httpSecurity
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        logger.info("httpSecurity 동작");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        "/loginUser"
                )

                // allow anonymous resource requests
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                );
    }
}

