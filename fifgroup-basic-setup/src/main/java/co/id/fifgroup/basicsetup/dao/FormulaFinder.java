package co.id.fifgroup.basicsetup.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.basicsetup.dto.FormulaDTO;
import co.id.fifgroup.basicsetup.dto.FormulaVersionDTO;

public interface FormulaFinder {
	public int getCountFormulaDtoByFormulaNameAndDescription(@Param("formulaName") String formulaName, @Param("description") String description);
	public List<FormulaDTO> getFormulaDtoByFormulaNameAndDescription(@Param("formulaName")String formulaName, @Param("description") String description);
	public FormulaDTO getFormulaDtoById(@Param("id") Long id);
	public List<Integer> findVersionsById(@Param("id") Long id);
	public FormulaDTO findByIdAndVersionNumber(@Param("id")Long id, @Param("versionNumber")Integer versionNumber);
	public int getNextVersionIdByFormulaId(@Param("id")Long id);
	public FormulaDTO getFormulaDtoByIdAndVersionNumber(@Param("id")Long id,@Param("currentVersionNumber")int currentVersionNumber);
	public int getCurrentFormulaVersionByFormulaId(@Param("id")Long id);
	public int isFormulaVersionFutureExist(@Param("id")Long id);
	public FormulaDTO getCurrentFormulaDto(@Param("formulaId") Long formulaId, @Param("sysDate") Date sysDate);
	public FormulaDTO getLastFormulaDto(@Param("formulaId") Long formulaId);
	public int getCountFormulaVersionByFormulaId(@Param("formulaId") Long formulaId);
	public FormulaVersionDTO getFormulaVersionDtoByFormulaNameAndEffectiveOnDate(@Param("formulaName") String formulaName, @Param("effectiveOnDate") Date effectiveOnDate);
	public List<FormulaDTO> getFormulaDtoByExample(@Param("criteria") FormulaDTO example);
	public int getCountFormulaByExample(@Param("criteria") FormulaDTO example);
	public FormulaDTO getFormulaDtoByIdAndDate(@Param("id")Long id, @Param("effectiveDate")Date effectiveDate); 
	public List<FormulaDTO> getFormulaHeaderDtoByExampleWithRowbounds(@Param("criteria") FormulaDTO example, RowBounds rowbounds);
	public int getCountFormulaByIdAndDate(@Param("id")Long id, @Param("effectiveDate")Date effectiveDate);
}
