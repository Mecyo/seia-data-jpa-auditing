/**
 * 
 */
package br.gov.ba.seia.auditing.model.generic;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import br.gov.ba.seia.auditing.GenericEntityListener;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
@MappedSuperclass
@EntityListeners(GenericEntityListener.class)
public abstract class BaseAuditableEntity extends Auditable<String> implements Serializable {

	protected static final long serialVersionUID = -4681893312842179698L;
	
	/**
	 * Implementação obrigatória em caso de uso da library LogAuditoria
	 * @return
	 */
	public abstract Integer getId();
}
