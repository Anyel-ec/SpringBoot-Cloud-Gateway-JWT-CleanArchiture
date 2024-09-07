package top.anyel.gateway.domain.model.dto;

public record SignUpDTO(String name,
                        String lastName,
                        String email,
                        String password) {}