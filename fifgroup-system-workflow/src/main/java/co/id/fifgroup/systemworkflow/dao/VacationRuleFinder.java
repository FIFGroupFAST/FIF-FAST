package co.id.fifgroup.systemworkflow.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.dto.VacationRuleDTO;

public interface VacationRuleFinder {

	public int countActiveVacationRule(@Param("approverId") UUID approverId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public VacationRule getActiveVacationRuleByApproverId(@Param("approverId") UUID approverId, @Param("effectiveOnDate") Date effectiveOnDate);
	
	public List<VacationRuleDTO> getVacationRule(@Param("approverName") String approverName, @Param("substituteName") String substituteName, 
			@Param("dateFrom") Date effectiveDateFrom, @Param("dateTo") Date effectiveDateTo);
	
	public List<VacationRuleDTO> getVacationRuleByApproverId(@Param("approverId") UUID approverId, @Param("substituteName") String substituteName, 
			@Param("dateFrom") Date effectiveDateFrom, @Param("dateTo") Date effectiveDateTo);
}
