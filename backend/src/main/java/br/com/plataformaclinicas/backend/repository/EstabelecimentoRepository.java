package br.com.plataformaclinicas.backend.repository;

import br.com.plataformaclinicas.backend.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {}