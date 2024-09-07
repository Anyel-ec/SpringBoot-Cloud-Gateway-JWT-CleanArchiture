package top.anyel.gateway.domain.usecase;

import top.anyel.gateway.domain.model.dto.LogInDTO;
import top.anyel.gateway.domain.model.dto.TokenDTO;
import top.anyel.gateway.domain.model.user.gateway.UserGateway;
import reactor.core.publisher.Mono;

public class LogInUseCase {


    private final UserGateway userGateway;

    public LogInUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Mono<TokenDTO> login(LogInDTO dto) {
        return userGateway.login(dto);
    }
}