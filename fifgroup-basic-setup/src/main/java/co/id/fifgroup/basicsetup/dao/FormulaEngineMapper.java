package co.id.fifgroup.basicsetup.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FormulaEngineMapper {

	public List<Map<String, Object>> getFromQuery(@Param("sql") String sql);
}
