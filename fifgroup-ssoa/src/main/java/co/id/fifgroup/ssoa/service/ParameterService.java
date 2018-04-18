package co.id.fifgroup.ssoa.service;

import co.id.fifgroup.ssoa.domain.Parameter;
import co.id.fifgroup.ssoa.domain.ParameterDetail;
import co.id.fifgroup.ssoa.domain.ParameterDetailExample;
import co.id.fifgroup.ssoa.domain.ParameterExample;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.ParameterDetailDTO;

import java.util.List;


public interface ParameterService {
	
	public void save(ParameterDTO taskRunnerDto) throws Exception;
	
	public void delete(ParameterDTO parameterDto) throws Exception;
	
	public List<ParameterDTO> getParameterDtoByExample(Parameter example, int limit, int offset, Long taskGroupId);
	
	public ParameterDTO getParameterById(String id);
	
	public List<Parameter> getParameterByExample(ParameterExample example, int limit, int offset);
	public List<Parameter> getParameterByExample(ParameterExample example);
	
	public int countParameterByExample(ParameterExample example, Long taskGroupId);
	public int countByExample(Parameter example, Long taskGroupId);
	
	public List<ParameterDetail> getParameterDetailByParameterId(String id);
	public List<ParameterDetailDTO> getParameterDetailDtoByParameterId(String id);
	
	public void updateTaskRequestDetailByPrimaryKey(ParameterDetail taskRunnerDetail);
	
	public List<ParameterDetail> getParameterDetailByExample(ParameterDetailExample example);
	
}
