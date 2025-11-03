package br.com.plataformaclinicas.backend.controller;

import br.com.plataformaclinicas.backend.model.Estabelecimento;
import br.com.plataformaclinicas.backend.model.HorarioAtendimento;
import br.com.plataformaclinicas.backend.repository.EstabelecimentoRepository;
import br.com.plataformaclinicas.backend.repository.HorarioAtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/v1/horarios")
public class HorarioAtendimentoController {

    @Autowired
    private HorarioAtendimentoRepository horarioRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public record HorarioRequest(
            Integer diaDaSemana,
            LocalTime horaInicio,
            LocalTime horaFim,
            Long estabelecimentoId
    ) {}

    @PostMapping
    public ResponseEntity<?> criarHorario(
            @RequestBody HorarioRequest request,
            Authentication authentication
    ) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(request.estabelecimentoId())
                .orElse(null);

        if (estabelecimento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estabelecimento não encontrado.");
        }

        String emailDonoToken = authentication.getName();
        String emailDonoEstabelecimento = estabelecimento.getUsuario().getEmail();

        if (!emailDonoToken.equals(emailDonoEstabelecimento)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Você não tem permissão para adicionar horários a este estabelecimento.");
        }

        HorarioAtendimento novoHorario = new HorarioAtendimento();
        novoHorario.setDiaDaSemana(request.diaDaSemana());
        novoHorario.setHoraInicio(request.horaInicio());
        novoHorario.setHoraFim(request.horaFim());
        novoHorario.setEstabelecimento(estabelecimento);

        HorarioAtendimento horarioSalvo = horarioRepository.save(novoHorario);

        return ResponseEntity.ok(horarioSalvo);
    }
}