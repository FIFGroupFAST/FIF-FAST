package co.id.fifgroup.eligibility.finder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableRowDTO;

public interface DecisionTableFinder {
	DecisionTableDTO findById( @Param("modulePrefix") String modulePrefix, @Param("id") Long id);
	
	// used by housing for performance issue [15051914073236] - 28/05/2015 | PHI
	DecisionTableDTO findByIdAndConditions( @Param("modulePrefix") String modulePrefix, @Param("id") Long id, @Param("conditions") String conditions);
	DecisionTableDTO findHeaderById( @Param("modulePrefix") String modulePrefix, @Param("id") Long id);
	
	/**
	 * [16022316000474] HCMS ph 2- CAM - performance upload career path
	 * By Jatis (HS) 24/03/2016
	 */
	List<DecisionTableRowDTO> findRowById( @Param("modulePrefix") String modulePrefix, @Param("id") Long id);
	// [16022316000474]
	
	// 15051913370470 - Improve performance for eligibility task runner by adding conditions (nullable) | By : PHI
	// List<DecisionTableRowDTO> findRowsById( @Param("modulePrefix") String modulePrefix, @Param("id") Long id);
	List<DecisionTableRowDTO> findRowsByIdAndConditions(@Param("modulePrefix") String modulePrefix, @Param("id") Long id, @Param("conditions") String conditions);

	// end used by housing for performance issue [15051914073236] - 28/05/2015 | PHI

	
	//start
	//added by jatis; SIT CAM
	// 28 oktober 2015
	DecisionTableDTO findByIdAndRowBound(@Param("modulePrefix")String modulePrefix, @Param("id")Long id, @Param("rowFrom")Long rowFrom, @Param("rowEnd")Long rowEnd);
	DecisionTableDTO findByIdWithoutDRL( @Param("modulePrefix") String modulePrefix, @Param("id") Long id);
	DecisionTableDTO findByCriteriaWithoutDRL(@Param("modulePrefix")String modulePrefix, @Param("criteria")Map<String, Object> criteria);
	//end added by jatis; SIT CAM

	
}
