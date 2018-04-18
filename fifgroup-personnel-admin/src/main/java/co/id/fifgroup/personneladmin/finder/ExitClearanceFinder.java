package co.id.fifgroup.personneladmin.finder;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.dto.ExitClearanceDTO;

public interface ExitClearanceFinder {

	public List<ExitClearanceDTO> selectByExample(ExitClearanceDTO exitClearanceDTO);
	
	public List<ExitClearanceDTO> selectByExampleWithRowbounds(ExitClearanceDTO exitClearanceDTO, RowBounds rowBounds);
	
	public Integer countByExample(ExitClearanceDTO exitClearanceDTO);
}
