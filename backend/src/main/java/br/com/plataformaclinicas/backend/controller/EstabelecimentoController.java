package br.com.plataformaclinicas.backend.controller;

import br.com.plataformaclinicas.backend.model.Estabelecimento;
import br.com.plataformaclinicas.backend.model.Usuario;
import br.com.plataformaclinicas.backend.repository.EstabelecimentoRepository;
import br.com.plataformaclinicas.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Estabelecimento> criar(
            @RequestBody Estabelecimento novoEstabelecimento,
            Authentication authentication
    ) {
        String emailDoUsuarioLogado = authentication.getName();

        Usuario usuarioDono = usuarioRepository.findByEmail(emailDoUsuarioLogado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário do token não encontrado"));

        novoEstabelecimento.setUsuario(usuarioDono);

        Estabelecimento estabelecimentoSalvo = estabelecimentoRepository.save(novoEstabelecimento);

        return ResponseEntity.ok(estabelecimentoSalvo);
    }
}