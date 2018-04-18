package co.id.fifgroup.ssoa.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;

public interface RetirementFinder {
	
	List<RetirementDTO> selectByExample(RetirementExample example, RowBounds rowBounds);
	
	List<RetirementDTO> selectByExample(RetirementExample example);
	
	List<RetirementDetailDTO> selectDetailByHeaderId(@Param("id") Long id);
	
	int countByExample(RetirementExample example);
	
	
}
