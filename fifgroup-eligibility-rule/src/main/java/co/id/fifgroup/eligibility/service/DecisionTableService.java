package co.id.fifgroup.eligibility.service;

import java.util.Date;
import java.util.Map;

import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.Results;

public interface DecisionTableService {

	DecisionTableDTO findDecisionTableById(String modulePrefix, Long id, boolean cached);
	
	DecisionTableDTO findDecisionTableById(String modulePrefix, Long id);
	
	/**
	 * [16022316000474] HCMS ph 2- CAM - performance upload career path
	 * By Jatis (HS) 24/03/2016
	 */
	DecisionTableDTO findDecisionTableHeaderById(String modulePrefix, Long id);
	//16022316000474
	
	
	// used by housing for performance issue [15051914073236] - 28/05/2015 | PHI
	DecisionTableDTO findDecisionTableByIdAndConditions(String modulePrefix, Long id, String conditions);
	
	DecisionTableDTO findDecisionTableByIdRestructured(String modulePrefix, Long id);
	// end used by housing for performance issue [15051914073236] - 28/05/2015 | PHI
	
	void save(DecisionTableDTO subject);
	
	void save(String modulePrefix, DecisionTableDTO subject);
	
	Results execute(String modulePrefix, Long decisionTableId, Long personId, Date effectiveOnDate, Map<String, Object> additionalParams);
	
	Results execute(String modulePrefix, Long decisionTableId, Long personId, Date effectiveOnDate, Map<String, Object> additionalParams, boolean useCache);
	
	void delete(DecisionTableDTO subject);
	
	void delete(String modulePrefix, DecisionTableDTO subject);
	
	void flushFactTypeCache();
	
	void populateCacheTable(Long companyId);
	
	void populatePayrollCache(Long companyId);
	
	//start
	//added by jatis; SIT CAM
	// 28 oktober 2015
	DecisionTableDTO findDecisionTableById(String modulePrefix, Long id, Long rowFrom, Long rowEnd);
	DecisionTableDTO findByIdWithoutDRL(String modulePrefix, Long id);
	DecisionTableDTO findByCriteriaWithoutDRL(Map<String, Object> criteria, String modulePrefix);
	//end added by jatis; SIT CAM

	
}
