package top.anyel.gateway.domain.usecase;

import top.anyel.gateway.domain.model.dto.SignUpDTO;
import top.anyel.gateway.domain.model.user.User;
import top.anyel.gateway.domain.model.user.gateway.UserGateway;
import reactor.core.publisher.Mono;

public class SignUpUseCase {

    private final UserGateway userGateway;


    public SignUpUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Mono<User> signUp(SignUpDTO dto) {
        return userGateway.signUp(dto);
    }
}
