package co.id.fifgroup.eligibility.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.eligibility.constant.ColumnType;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;

public interface DecisionTableModelFinder {

	DecisionTableModelDTO findByIdAndVersionNumber(@Param("id") String id,@Param("versionNumber") Integer versionNumber);
	
	List<DecisionTableModelDTO> findByExample(DecisionTableModelDTO example);
	
	List<DecisionTableModelDTO> search(DecisionTableModelDTO example);
	
	Integer countByExample(DecisionTableModelDTO example);
	
	List<DecisionTableColumnDTO> findColumns(@Param("id") String id, @Param("versionNumber")  Integer versionNumber, @Param("columnType")  ColumnType columnType);

	List<Integer> findAvailableVersions(String id);
	
	Integer getNextVersion(String id);
	
	
}

