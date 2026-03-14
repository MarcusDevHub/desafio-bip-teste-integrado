package com.example.backend;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioService {

    private final BeneficioRepository repository;

    public BeneficioService(BeneficioRepository repository) {
        this.repository = repository;
    }

    public List<Beneficio> listarTodos() {
        return repository.findAll();
    }

    public Beneficio buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Benefício não encontrado."));
    }

    public Beneficio criar(Beneficio beneficio) {
        beneficio.setId(null);
        return repository.save(beneficio);
    }

    public Beneficio atualizar(Long id, Beneficio dados) {
        Beneficio existente = buscarPorId(id);

        existente.setNome(dados.getNome());
        existente.setDescricao(dados.getDescricao());
        existente.setValor(dados.getValor());
        existente.setAtivo(dados.getAtivo());

        return repository.save(existente);
    }

    public void deletar(Long id) {
        Beneficio existente = buscarPorId(id);
        repository.delete(existente);
    }

    public void transferir(Long fromId, Long toId, BigDecimal amount) {
        if (fromId == null || toId == null) {
            throw new BusinessException("Os ids de origem e destino são obrigatórios.");
        }

        if (fromId.equals(toId)) {
            throw new BusinessException("Origem e destino não podem ser o mesmo benefício.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("O valor da transferência deve ser maior que zero.");
        }

        Beneficio from = buscarPorId(fromId);
        Beneficio to = buscarPorId(toId);

        if (from.getValor().compareTo(amount) < 0) {
            throw new BusinessException("Saldo insuficiente para realizar a transferência.");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        repository.save(from);
        repository.save(to);
    }

}
