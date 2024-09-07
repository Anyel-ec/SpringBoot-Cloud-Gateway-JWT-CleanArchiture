package top.anyel.gateway.infrastructure.drivenadapter.mongodb.repository;

import top.anyel.gateway.infrastructure.drivenadapter.mongodb.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveMongoRepository extends ReactiveMongoRepository<UserDocument, String> {
    Mono<UserDocument> findByEmail(String email);

}