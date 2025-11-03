package br.com.plataformaclinicas.backend.controller;

import br.com.plataformaclinicas.backend.model.Estabelecimento;
import br.com.plataformaclinicas.backend.model.Servico;
import br.com.plataformaclinicas.backend.repository.EstabelecimentoRepository;
import br.com.plataformaclinicas.backend.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public record ServicoRequest(
            String nome,
            String descricao,
            BigDecimal valor,
            Long estabelecimentoId
    ) {}

    @PostMapping
    public ResponseEntity<?> criarServico(
            @RequestBody ServicoRequest request,
                                          Authentication authentication
    ) {

        Long estabelecimentoId = request.estabelecimentoId();
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElse(null);

        if (estabelecimento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estabelecimento não encontrado.");
        }

        String emailDonoToken = authentication.getName();
        String emailDonoEstabelecimento = estabelecimento.getUsuario().getEmail();

        if (!emailDonoEstabelecimento.equals(emailDonoEstabelecimento)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para adicionar serviços a este estabelecimento.");
        }

        Servico novoServico = new Servico();
        novoServico.setNome(request.nome());
        novoServico.setDescricao(request.descricao());
        novoServico.setValor(request.valor());
        novoServico.setEstabelecimento(estabelecimento);

        Servico servicoSalvo = servicoRepository.save(novoServico);

        return ResponseEntity.ok(servicoSalvo);
    }
}
