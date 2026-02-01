package com.example.minishop.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeResponse {
    private String email;
    private String role;
}
