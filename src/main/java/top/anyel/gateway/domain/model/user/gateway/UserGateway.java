package top.anyel.gateway.domain.model.user.gateway;

import top.anyel.gateway.domain.model.dto.LogInDTO;
import top.anyel.gateway.domain.model.dto.SignUpDTO;
import top.anyel.gateway.domain.model.dto.TokenDTO;
import top.anyel.gateway.domain.model.user.User;
import reactor.core.publisher.Mono;

public interface UserGateway {
    Mono<User> signUp(SignUpDTO dto);
    Mono<TokenDTO> login(LogInDTO dto);

}