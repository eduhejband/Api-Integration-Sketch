package com.example.medicamentos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "nominationClient", url = "https://nominatim.openstreetmap.org")
public interface NominationClient {

    @GetMapping("/search")
    List<NominatimResponse> buscarLocalizacao(@RequestParam("q") String query,
                                              @RequestParam("format") String format);
}



