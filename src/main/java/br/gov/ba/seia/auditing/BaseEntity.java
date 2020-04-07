/**
 * 
 */
package br.gov.ba.seia.auditing;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
@MappedSuperclass
@EntityListeners(GenericEntityListener.class)
public abstract class BaseEntity extends Auditable<String> implements Serializable {

	protected static final long serialVersionUID = -4681893312842179698L;
	
	/**
	 * Implementação obrigatória em caso de uso da library LogAuditoria
	 * @return
	 */
	protected abstract Integer getId();
}
