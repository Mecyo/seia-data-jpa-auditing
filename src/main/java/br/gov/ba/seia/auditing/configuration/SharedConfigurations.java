/**
 * 
 */
package br.gov.ba.seia.auditing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
@Configuration
public class SharedConfigurations {

	@Bean
	public Jackson2ObjectMapperBuilder mappingJackson2Object() {
		final String[] propertiesExcludeJson = new String[] { "createdBy", "createdDate", "lastModifiedBy",
				"lastModifiedDate" };

		SimpleBeanPropertyFilter sp = SimpleBeanPropertyFilter.serializeAllExcept(propertiesExcludeJson);

		FilterProvider filters = new SimpleFilterProvider()
				.setDefaultFilter(SimpleBeanPropertyFilter.serializeAllExcept(propertiesExcludeJson))
				.addFilter("logAuditFilter", sp).setFailOnUnknownId(false);

		return new Jackson2ObjectMapperBuilder().filters(filters);
	}
}
