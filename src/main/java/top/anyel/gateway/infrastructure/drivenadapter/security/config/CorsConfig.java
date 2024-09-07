package top.anyel.gateway.infrastructure.drivenadapter.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOriginPattern("http://localhost:4200"); // Permitir todas las URLs con localhost:4200
        corsConfig.addAllowedMethod("*"); // Permitir todos los métodos HTTP
        corsConfig.addAllowedHeader("*"); // Permitir todos los encabezados
        corsConfig.setAllowCredentials(true); // Permitir el envío de credenciales como cookies o auth headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
