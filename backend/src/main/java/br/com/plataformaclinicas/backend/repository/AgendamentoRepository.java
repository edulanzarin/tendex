package br.com.plataformaclinicas.backend.repository;

import br.com.plataformaclinicas.backend.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByServicoEstabelecimentoIdAndDataHoraInicioBetween(
            Long estabelecimentoId,
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim
    );
}