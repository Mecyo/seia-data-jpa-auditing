package br.gov.ba.seia.auditing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 * @param <T>
 */
@Repository
interface LogAuditoriaRepository<T> extends JpaRepository<LogAuditoria<T>, Integer> {
}