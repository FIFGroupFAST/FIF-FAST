package co.id.fifgroup.workstructure.finder;

import org.apache.ibatis.annotations.Param;

public interface HistoryFinder {

	Integer getHistories(@Param("tableName") String tableName, @Param("idName") String idName, @Param("id") Long id);
}
