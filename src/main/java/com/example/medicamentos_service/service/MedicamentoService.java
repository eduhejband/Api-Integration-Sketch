package com.example.medicamentos_service.service;

import com.example.medicamentos_service.client.NominationClient;
import com.example.medicamentos_service.client.NominatimResponse;
import com.example.medicamentos_service.dao.MedicamentoDAO;
import com.example.medicamentos_service.dto.MedicamentoDTO;
import com.example.medicamentos_service.mapper.MedicamentoMapper;
import com.example.medicamentos_service.model.Medicamento;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicamentoService {
    private final MedicamentoDAO medicamentoDAO;
    private final MedicamentoMapper medicamentoMapper;
    private final NominationClient nominationClient;

    public MedicamentoService(MedicamentoDAO medicamentoDAO, MedicamentoMapper medicamentoMapper, NominationClient nominationClient) {
        this.medicamentoDAO = medicamentoDAO;
        this.medicamentoMapper = medicamentoMapper;
        this.nominationClient = nominationClient;
    }

    @RateLimiter(name = "medicamentoService", fallbackMethod = "fallbackMethod")
    public MedicamentoDTO salvar(MedicamentoDTO medicamentoDTO) {
        Medicamento medicamento = medicamentoMapper.toEntity(medicamentoDTO);
        List<NominatimResponse> localizacao = nominationClient.buscarLocalizacao(medicamentoDTO.localizacao(), "json");

        if (localizacao.isEmpty()) {
            throw new RuntimeException("Localização não encontrada para o medicamento: " + medicamentoDTO.nome());
        }

        medicamento = medicamentoDAO.salvar(medicamento);
        return medicamentoMapper.toDTO(medicamento);
    }

    @RateLimiter(name = "medicamentoService", fallbackMethod = "fallbackMethod")
    public Optional<MedicamentoDTO> buscarPorId(Long id) {
        return medicamentoDAO.buscarPorId(id)
                .map(medicamentoMapper::toDTO);
    }

    @RateLimiter(name = "medicamentoService", fallbackMethod = "fallbackMethod")
    public List<MedicamentoDTO> buscarTodos() {
        return medicamentoDAO.buscarTodos()
                .stream()
                .map(medicamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @RateLimiter(name = "medicamentoService", fallbackMethod = "fallbackMethod")
    public Optional<MedicamentoDTO> atualizar(Long id, MedicamentoDTO medicamentoDTO) {
        Optional<Medicamento> medicamentoExistente = medicamentoDAO.buscarPorId(id);

        if (medicamentoExistente.isPresent()) {
            Medicamento medicamento = medicamentoMapper.toEntity(medicamentoDTO);
            medicamento.setId(id);
            medicamentoDAO.atualizar(medicamento);
            return Optional.of(medicamentoMapper.toDTO(medicamento));
        }
        return Optional.empty();
    }

    @RateLimiter(name = "medicamentoService", fallbackMethod = "fallbackMethod")
    public boolean deletar(Long id) {
        return medicamentoDAO.deletar(id);
    }

    private MedicamentoDTO fallbackMethod(MedicamentoDTO medicamentoDTO, Throwable t) {
        throw new RuntimeException("Muitas requisições! Aguarde e tente novamente.");
    }

    private Optional<MedicamentoDTO> fallbackMethod(Long id, Throwable t) {
        throw new RuntimeException("Muitas requisições! Aguarde e tente novamente.");
    }
}
