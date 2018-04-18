package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.basicsetup.dto.FormulaParameterDTO;

public interface FormulaParameterFinder {
	public int getCountFormulaParameterDtoByParameterName(@Param("parameterName") String parameterName);
	public List<FormulaParameterDTO> getFormulaParameterDtoByParameterNameWithRowbounds(@Param("parameterName") String parameterName, RowBounds rowBounds);
	public int getCountFormulaParameterDtoByParameterNameAndDescription(@Param("parameterName")String parameterName, @Param("description") String description);
	public List<FormulaParameterDTO> getFormulaParameterDtoByParameterNameAndDescription(@Param("parameterName") String parameterName, @Param("description") String description);
	
}
