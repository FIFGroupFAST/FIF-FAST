package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.basicsetup.domain.GLCode;

public interface GLCodeMapper {

	public List<GLCode> getGlCodeBySegmentNum(@Param("segmentNum") Long segmentNum);
	
}
