/**
 * 
 */
package br.gov.ba.seia.auditing.model.generic;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
public class CustomEntitySerializer extends StdSerializer<BaseAuditableEntity> {

	private static final long serialVersionUID = -2843784757074773193L;

	public CustomEntitySerializer() {
		this(null);
	}

	public CustomEntitySerializer(Class<BaseAuditableEntity> t) {
		super(t);
	}

	@Override
	public void serialize(BaseAuditableEntity obj, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		generator.writeObject(obj.getId());
	}
}
