package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import jakarta.persistence.LockModeType;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (fromId == null || toId == null) {
            throw new IllegalArgumentException("Os ids de origem e destino são obrigatórios.");
        }

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Origem e destino não podem ser o iguais.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }

        // Utiliza bloqueio PESSIMISTIC_WRITE para evitar condição de transações
        // modificando os mesmos registros simultaneamente

        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

        if (from == null) {
            throw new IllegalArgumentException("Benefício de origem não encontrado.");
        }

        if (to == null) {
            throw new IllegalArgumentException("Benefício de destino não encontrado.");
        }

        if (from.getValor().compareTo(amount) <= 0) {
            throw new IllegalStateException("Saldo insuficiente para realizar a transferência.");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));
    }
}
