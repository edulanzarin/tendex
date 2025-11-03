package br.com.plataformaclinicas.backend.repository;

import br.com.plataformaclinicas.backend.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {}
