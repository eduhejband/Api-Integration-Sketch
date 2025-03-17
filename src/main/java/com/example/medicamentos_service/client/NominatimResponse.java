package com.example.medicamentos_service.client;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor // 🔥 Garante que Feign consiga instanciar corretamente
public class NominatimResponse {
    private String display_name;
    private double lat;
    private double lon;
}
