package br.com.plataformaclinicas.backend.controller;

import br.com.plataformaclinicas.backend.model.Usuario;
import br.com.plataformaclinicas.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario novoUsuario) {

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok(usuarioSalvo);
    }
}
