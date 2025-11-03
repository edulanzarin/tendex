package br.com.plataformaclinicas.backend.repository;

import br.com.plataformaclinicas.backend.model.HorarioAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioAtendimentoRepository extends JpaRepository<HorarioAtendimento, Long> {}