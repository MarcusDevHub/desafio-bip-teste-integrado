package com.example.backend;

import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Benefício não encontrado."));
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
}
