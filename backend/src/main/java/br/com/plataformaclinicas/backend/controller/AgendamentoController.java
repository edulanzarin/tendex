package br.com.plataformaclinicas.backend.controller;

import br.com.plataformaclinicas.backend.model.Agendamento;
import br.com.plataformaclinicas.backend.model.HorarioAtendimento;
import br.com.plataformaclinicas.backend.model.Servico;
import br.com.plataformaclinicas.backend.repository.AgendamentoRepository;
import br.com.plataformaclinicas.backend.repository.HorarioAtendimentoRepository;
import br.com.plataformaclinicas.backend.repository.ServicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private HorarioAtendimentoRepository horarioRepository;

    public record AgendamentoRequest(
            Long servicoId,
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim,
            String nomeCliente,
            String telefoneCliente
    ) {}

    @PostMapping
    public ResponseEntity<?> criarAgendamento(@RequestBody AgendamentoRequest request) {

        Servico servico = servicoRepository.findById(request.servicoId()).orElse(null);
        if (servico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado.");
        }

        Long estabelecimentoId = servico.getEstabelecimento().getId();

        boolean horarioValido = verificarHorarioAtendimento(
                estabelecimentoId,
                request.dataHoraInicio(),
                request.dataHoraFim()
        );

        if (!horarioValido) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O horário solicitado está fora do horário de atendimento.");
        }

        List<Agendamento> agendamentosConflitantes = agendamentoRepository
                .findByServicoEstabelecimentoIdAndDataHoraInicioBetween(
                        estabelecimentoId,
                        request.dataHoraInicio().minusMinutes(1),
                        request.dataHoraFim().plusMinutes(1)
                );

        if (!agendamentosConflitantes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Este horário já está ocupado.");
        }

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setServico(servico);
        novoAgendamento.setDataHoraInicio(request.dataHoraInicio());
        novoAgendamento.setDataHoraFim(request.dataHoraFim());
        novoAgendamento.setNomeCliente(request.nomeCliente());
        novoAgendamento.setTelefoneCliente(request.telefoneCliente());

        Agendamento agendamentoSalvo = agendamentoRepository.save(novoAgendamento);
        return ResponseEntity.ok(agendamentoSalvo);
    }

    private boolean verificarHorarioAtendimento(Long estabelecimentoId, LocalDateTime inicio, LocalDateTime fim) {
        int diaDaSemana = inicio.getDayOfWeek().getValue();
        if (diaDaSemana == 7) diaDaSemana = 0;

        List<HorarioAtendimento> horariosDoDia = horarioRepository
                .findByEstabelecimentoIdAndDiaDaSemana(estabelecimentoId, diaDaSemana);

        if (horariosDoDia.isEmpty()) {
            return false;
        }

        for (HorarioAtendimento horario : horariosDoDia) {
            boolean estaDentro =
                    !inicio.toLocalTime().isBefore(horario.getHoraInicio()) &&
                            !fim.toLocalTime().isAfter(horario.getHoraFim());

            if (estaDentro) {
                return true;
            }
        }

        return false;
    }
}