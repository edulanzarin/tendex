package br.com.plataformaclinicas.backend.controller;

import br.com.plataformaclinicas.backend.model.Usuario;
import br.com.plataformaclinicas.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario novoUsuario) {

        novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok(usuarioSalvo);
    }
}
