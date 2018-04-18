package co.id.fifgroup.core.util;

import org.apache.ibatis.annotations.Param;


public interface IdGenerator {
	
	Long getNextHeaderId(@Param("sequenceName") String sequenceName);
}
