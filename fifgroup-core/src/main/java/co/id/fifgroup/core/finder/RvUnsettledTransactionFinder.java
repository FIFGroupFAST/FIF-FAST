package co.id.fifgroup.core.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.dto.RVUnsettledDTO;

public interface RvUnsettledTransactionFinder {

	public List<RVUnsettledDTO> getRvUnsettledTransaction(@Param("companyId") Long companyId);
}
