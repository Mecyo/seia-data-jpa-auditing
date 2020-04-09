/**
 * 
 */
package br.gov.ba.seia.auditing;

import org.springframework.data.domain.AuditorAware;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
public interface AuditorAwareInformations extends AuditorAware<String> {

	public String getClientIpAddress();
	
	public String getClientHost();
}
