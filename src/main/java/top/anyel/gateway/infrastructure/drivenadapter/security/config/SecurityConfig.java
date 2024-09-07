package top.anyel.gateway.infrastructure.drivenadapter.security.config;


import top.anyel.gateway.infrastructure.drivenadapter.security.jwt.filter.JwtFilter;
import top.anyel.gateway.infrastructure.drivenadapter.security.repository.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;

    public SecurityConfig(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JwtFilter jwtFilter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec -> exchangeSpec
                        // Permitir acceso sin autenticación a las rutas de Django
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/django/api/v2/genders/**").permitAll()
                        .pathMatchers("/django/api/v2/provinces/**").permitAll()
                        // Permitir acceso sin autenticación a las rutas relacionadas con restConsumer
                        .pathMatchers("/springboot/restConsumer/**").permitAll()
                        // Cualquier otra ruta requiere autenticación
                        .anyExchange().authenticated())
                // Colocar el filtro JWT solo después de asegurar las rutas permitidas
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .securityContextRepository(securityContextRepository)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .build();
    }



}