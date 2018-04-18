package co.id.fifgroup.ssoa.service.impl;

import co.id.fifgroup.ssoa.dao.ParameterDetailMapper;
import co.id.fifgroup.ssoa.dao.ParameterMapper;
import co.id.fifgroup.ssoa.domain.Parameter;
import co.id.fifgroup.ssoa.domain.ParameterDetail;
import co.id.fifgroup.ssoa.domain.ParameterDetailExample;
import co.id.fifgroup.ssoa.domain.ParameterExample;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.ParameterDetailDTO;
import co.id.fifgroup.ssoa.finder.ParameterFinder;
import co.id.fifgroup.ssoa.service.ParameterService;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("parameterService")
public class ParameterServiceImpl implements ParameterService{
	
	//Task Runner Entity
	@Autowired
	private ParameterMapper parameterMapper;
	@Autowired
	private ParameterDetailMapper parameterDetailMapper;
	
	//Finder
	@Autowired
	private ParameterFinder parameterFinder;
	
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Override
	@Transactional
	public void save(ParameterDTO parameterDto) throws Exception{
		//System.out.println("paramCode=="+parameterDto.getParameterCode());
		Parameter parameter = parameterMapper.selectByPrimaryKey(parameterDto.getParameterCode());
		//System.out.println("parameter=="+parameter);
		if(parameter == null || parameter.getParameterCode() == null){
			parameterMapper.insert(parameterDto);
			for (ParameterDetailDTO parameterDetailDto : parameterDto.getParameterDetailDto()) {
				ParameterDetail parameterDetail = modelMapper.map(parameterDetailDto, ParameterDetail.class);
				//System.out.println("parameterDetailCode=="+parameterDetail.getParameterDtlCode());
				//System.out.println("parameterDetailName=="+parameterDetail.getParameterDtlName());
				parameterDetail.setParameterCode(parameterDto.getParameterCode());
				parameterDetail.setCreatedBy(parameterDto.getCreatedBy());
				parameterDetail.setCreationDate(parameterDto.getCreationDate());
				parameterDetail.setLastUpdateDate(parameterDto.getLastUpdateDate());
				//parameterDetail.setLastUpdatedBy(parameterDto.getLastUpdatedBy());
				parameterDetailMapper.insert(parameterDetail);
			}
		} else {
			parameterMapper.updateByPrimaryKeySelective(parameterDto);
			parameterDetailMapper.deleteByHeaderKey(parameterDto.getParameterCode());
			for (ParameterDetailDTO parameterDetailDto : parameterDto.getParameterDetailDto()) {
				ParameterDetail parameterDetail = modelMapper.map(parameterDetailDto, ParameterDetail.class);
				parameterDetail.setParameterCode(parameterDto.getParameterCode());
				parameterDetail.setCreatedBy(parameterDto.getCreatedBy());
				parameterDetail.setCreationDate(parameterDto.getCreationDate());
				parameterDetail.setLastUpdateDate(parameterDto.getLastUpdateDate());
				//parameterDetail.setLastUpdatedBy(parameterDto.getLastUpdatedBy());
				//System.out.println("dtlcode=="+parameterDetail.getParameterDtlCode());
				/*if(parameterDetail.getParameterDtlCode() != null)
					parameterDetailMapper.updateByPrimaryKeySelective(parameterDetail);
				else*/
					parameterDetailMapper.insert(parameterDetail);
			}
		}
		
	}
	
	@Override
	@Transactional
	public void delete(ParameterDTO parameterDto) throws Exception{
		
			parameterMapper.deleteByPrimaryKey(parameterDto.getParameterCode());
			parameterDetailMapper.deleteByHeaderKey(parameterDto.getParameterCode());
			
	}
	
	@Override
	@Transactional(readOnly=true)
	public ParameterDTO getParameterById(String id) {
		ParameterDTO parameterDto = new ParameterDTO();
		Parameter parameter = parameterMapper.selectByPrimaryKey(id);
		parameterDto = modelMapper.map(parameter, ParameterDTO.class);
		parameterDto.setParameterDetailDto(getParameterDetailDtoByParameterId(parameterDto.getParameterCode()));
		return parameterDto;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Parameter> getParameterByExample(ParameterExample example, int limit, int offset) {	
		return parameterMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int countParameterByExample(ParameterExample example, Long taskGroupId) {
		return parameterMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ParameterDetail> getParameterDetailByParameterId(String id) {
		ParameterDetailExample example = new ParameterDetailExample();
		example.createCriteria().andParameterHdrIdEqualTo(id);
		return parameterDetailMapper.selectByExample(example);
		
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<ParameterDetailDTO> getParameterDetailDtoByParameterId(String id) {
		ParameterDetailExample example = new ParameterDetailExample();
		example.createCriteria().andParameterHdrIdEqualTo(id);
		List<ParameterDetailDTO> returnList = new ArrayList<>();
		
		for(ParameterDetail trd : parameterDetailMapper.selectByExample(example)){
			ParameterDetailDTO trdDto = new ParameterDetailDTO();
			trdDto = modelMapper.map(trd, ParameterDetailDTO.class);
			/*trdDto.setMainTask(taskServiceImpl.getTaskById(trdDto.getTaskId()));
			if(trdDto.getSccuessTaskId() != null){
				if(trdDto.getSccuessTaskId() != -1){
					trdDto.setSuccessTask(taskServiceImpl.getTaskById(trdDto.getSccuessTaskId()));
				}
				if(trdDto.getErrorTask()!= null){
					if(trdDto.getErrorTaskId() != -1){
						trdDto.setErrorTask(taskServiceImpl.getTaskById(trdDto.getErrorTaskId()));
					}
				}
			}*/
			returnList.add(trdDto);
		
		}
		
		return returnList;
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<ParameterDTO> getParameterDtoByExample(Parameter example, int limit, int offset, Long taskGroupId) {
		return parameterFinder.selectByExample(example, new RowBounds(offset, limit), taskGroupId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Parameter> getParameterByExample(ParameterExample example) {
		return parameterMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(Parameter example, Long taskGroupId) {
		return parameterFinder.countByExample(example, taskGroupId);
	}

	@Override
	public void updateTaskRequestDetailByPrimaryKey(ParameterDetail parameterDetail) {
		parameterDetailMapper.updateByPrimaryKey(parameterDetail);
		
	}

	@Override
	public List<ParameterDetail> getParameterDetailByExample(
			ParameterDetailExample example) {
		return parameterDetailMapper.selectByExample(example);
	}

}
