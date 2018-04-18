package co.id.fifgroup.personneladmin.finder;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import co.id.fifgroup.personneladmin.dto.SPPHistoryDTO;

public interface SppHistoryFinder {

	public List<SPPHistoryDTO> getInquiryData(@Param("personId") Long personId);
	
	public List<SPPHistoryDTO> getInquiryDataSppDashboard(@Param("criteria") SPPHistoryDTO sppHistoryDTO);
}
