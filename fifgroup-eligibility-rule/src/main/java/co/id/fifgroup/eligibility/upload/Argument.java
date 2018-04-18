package co.id.fifgroup.eligibility.upload;

import java.io.Serializable;
import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Argument implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(Argument.class);

	private Type type;
	private String value;

	public Argument(Type type, String value) {
		this.type = type;
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public <T> T getActualValue() {
		return type.convert(value);
	}

	public static enum Type {
		MODULE(String.class), ID(Long.class);

		
		private Class<?> clazz;

		private Type(Class<?> clazz) {
			this.clazz = clazz;
		}

		@SuppressWarnings("unchecked")
		public <T> T convert(String value) {
			try {
				Constructor<?> constructor = clazz.getConstructor(String.class);
				return (T) constructor.newInstance(value);
			} catch (Exception ex) {
				LOG.error(ex.getMessage(), ex);
			}

			return null;

		}
	}
}
