package br.com.plataformaclinicas.backend.repository;


import br.com.plataformaclinicas.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
