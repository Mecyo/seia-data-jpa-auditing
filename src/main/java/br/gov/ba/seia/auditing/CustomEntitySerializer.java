/**
 * 
 */
package br.gov.ba.seia.auditing;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
public class CustomEntitySerializer extends StdSerializer<Teste> {

	private static final long serialVersionUID = -2843784757074773193L;

	public CustomEntitySerializer() {
		this(null);
	}

	public CustomEntitySerializer(Class<Teste> t) {
		super(t);
	}

	@Override
	public void serialize(Teste obj, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		generator.writeObject(obj.getId());
	}
}
