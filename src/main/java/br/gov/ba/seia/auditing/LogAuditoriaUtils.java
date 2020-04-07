/**
 * 
 */
package br.gov.ba.seia.auditing;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
public class LogAuditoriaUtils {

	/**
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj, String... propertiesExclude) {
		ObjectMapper jackson = new ObjectMapper();

		try {
			if (propertiesExclude != null) {
				SimpleBeanPropertyFilter sp = SimpleBeanPropertyFilter.serializeAllExcept(propertiesExclude);

				FilterProvider filters = new SimpleFilterProvider().addFilter("logAuditFilter", sp);

				return jackson.writer(filters).writeValueAsString(obj);
			} else {
				return jackson.writeValueAsString(obj);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param json
	 * @param objClass
	 * @return
	 */
	public static Object jsonToObject(String json, Class<?> objClass) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(json, objClass);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
