package co.id.fifgroup.systemadmin.service;

import java.util.List;

import co.id.fifgroup.systemadmin.domain.Function;
import co.id.fifgroup.systemadmin.domain.FunctionExample;
import co.id.fifgroup.systemadmin.dto.FunctionDTO;

public interface FunctionService {
	
	public void save(Function function) throws Exception;
	
	public List<Function> getFunctionByExample(FunctionExample example);
	
	public List<Function> getFunctionName();
	
	public List<Function> selectByExample(FunctionExample example, int limit, int offset);
	
	public List<FunctionDTO> findByExample(FunctionDTO functionDTO, int limit, int offset);
	
	public List<FunctionDTO> findByExample(FunctionDTO functionDTO);
	
	public Function getFunctionById(Long id);
	
	public int countByExample(FunctionExample example);
	
}
