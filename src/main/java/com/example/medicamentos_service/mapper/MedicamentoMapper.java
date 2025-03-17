package com.example.medicamentos_service.mapper;

import com.example.medicamentos_service.dto.MedicamentoDTO;
import com.example.medicamentos_service.model.Medicamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface MedicamentoMapper {
    MedicamentoDTO toDTO(Medicamento medicamento);
    Medicamento toEntity(MedicamentoDTO medicamentoDTO);
}

