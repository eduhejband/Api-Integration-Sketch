package com.example.medicamentos_service.service;

import com.example.medicamentos_service.client.NominationClient;
import com.example.medicamentos_service.client.NominatimResponse;
import com.example.medicamentos_service.dao.MedicamentoDAO;
import com.example.medicamentos_service.dto.MedicamentoDTO;
import com.example.medicamentos_service.mapper.MedicamentoMapper;
import com.example.medicamentos_service.model.Medicamento;
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

    public MedicamentoDTO salvar(MedicamentoDTO medicamentoDTO) {
        Medicamento medicamento = medicamentoMapper.toEntity(medicamentoDTO);

        // Chamando a API da Nominatim para buscar a localização
        List<NominatimResponse> localizacao = nominationClient.buscarLocalizacao(medicamentoDTO.localizacao(), "json");


        if (localizacao.isEmpty()) {
            throw new RuntimeException("Localização não encontrada para o medicamento: " + medicamentoDTO.nome());
        }

        medicamento = medicamentoDAO.salvar(medicamento);
        return medicamentoMapper.toDTO(medicamento);
    }


    public Optional<MedicamentoDTO> buscarPorId(Long id) {
        return medicamentoDAO.buscarPorId(id)
                .map(medicamentoMapper::toDTO);
    }

    public List<MedicamentoDTO> buscarTodos() {
        return medicamentoDAO.buscarTodos()
                .stream()
                .map(medicamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

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

    public boolean deletar(Long id) {
        return medicamentoDAO.deletar(id);
    }
}
