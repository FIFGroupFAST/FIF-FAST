package co.id.fifgroup.core.upload;

import java.util.Map;

public interface Converter<T> {

	T convert(Map<String, Object> params);

}
