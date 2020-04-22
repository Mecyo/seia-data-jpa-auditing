/**
 * 
 */
package br.gov.ba.seia.auditing.utils;

import java.io.IOException;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
public class LogAuditoriaUtils {

	/**
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj, Jackson2ObjectMapperBuilder mappingJackson2Object) {
		ObjectMapper jackson = new ObjectMapper();
		mappingJackson2Object.configure(jackson);

		try {
			return jackson.writeValueAsString(obj);
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
