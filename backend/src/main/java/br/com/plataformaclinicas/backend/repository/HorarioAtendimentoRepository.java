package br.com.plataformaclinicas.backend.repository;

import br.com.plataformaclinicas.backend.model.HorarioAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HorarioAtendimentoRepository extends JpaRepository<HorarioAtendimento, Long> {

    List<HorarioAtendimento> findByEstabelecimentoIdAndDiaDaSemana(Long estabelecimentoId, Integer diaDaSemana);
}