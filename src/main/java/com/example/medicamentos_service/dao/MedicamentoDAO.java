package com.example.medicamentos_service.dao;

import com.example.medicamentos_service.model.Medicamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicamentoDAO {
    private final JdbcTemplate jdbcTemplate;

    public MedicamentoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Medicamento salvar(Medicamento medicamento) {
        String sql = "INSERT INTO medicamentos (nome, uso_controlado, localizacao) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, medicamento.getNome());
            ps.setBoolean(2, medicamento.isUsoControlado());
            ps.setString(3, medicamento.getLocalizacao());
            return ps;
        }, keyHolder);

        // Atribuindo o ID gerado ao objeto medicamento
        medicamento.setId(keyHolder.getKey().longValue());

        return medicamento;
    }

    public Optional<Medicamento> buscarPorId(Long id) {
        String sql = "SELECT * FROM medicamentos WHERE id = ?";
        List<Medicamento> medicamentos = jdbcTemplate.query(sql, getRowMapper(), id);
        return medicamentos.stream().findFirst();
    }

    public List<Medicamento> buscarTodos() {
        String sql = "SELECT * FROM medicamentos";
        return jdbcTemplate.query(sql, getRowMapper());
    }

    public void atualizar(Medicamento medicamento) {
        String sql = "UPDATE medicamentos SET nome = ?, uso_controlado = ?, localizacao = ? WHERE id = ?";
        jdbcTemplate.update(sql, medicamento.getNome(), medicamento.isUsoControlado(), medicamento.getLocalizacao(), medicamento.getId());
    }

    public boolean deletar(Long id) {
        String sql = "DELETE FROM medicamentos WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private RowMapper<Medicamento> getRowMapper() {
        return (rs, rowNum) -> new Medicamento(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getBoolean("uso_controlado"),
                rs.getString("localizacao")
        );
    }
}
