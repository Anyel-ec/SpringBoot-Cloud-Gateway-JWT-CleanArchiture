package top.anyel.gateway.app.config.usecase;

import top.anyel.gateway.domain.model.user.gateway.UserGateway;
import top.anyel.gateway.domain.usecase.LogInUseCase;
import top.anyel.gateway.domain.usecase.SignUpUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public SignUpUseCase signUpUseCase(UserGateway userGateway) {
        return new SignUpUseCase(userGateway);
    }

    @Bean
    public LogInUseCase logInUseCase(UserGateway userGateway) {
        return new LogInUseCase(userGateway);
    }
}