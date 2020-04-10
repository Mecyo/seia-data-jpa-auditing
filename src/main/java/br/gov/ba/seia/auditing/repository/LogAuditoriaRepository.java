package br.gov.ba.seia.auditing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ba.seia.auditing.model.LogAuditoria;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 * @param <T>
 */
@Repository
public interface LogAuditoriaRepository<T> extends JpaRepository<LogAuditoria<T>, Integer> {
}