package co.id.fifgroup.systemadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.systemadmin.dao.TaskRunnerDetailMapper;
import co.id.fifgroup.systemadmin.dao.TaskRunnerMapper;
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetail;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetailExample;
import co.id.fifgroup.systemadmin.domain.TaskRunnerExample;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;
import co.id.fifgroup.systemadmin.finder.TaskRunnerFinder;
import co.id.fifgroup.systemadmin.service.TaskRunnerService;
import co.id.fifgroup.systemadmin.service.TaskService;

@Transactional
@Service("taskRunnerService")
public class TaskRunnerServiceImpl implements TaskRunnerService{
	
	//Task Runner Entity
	@Autowired
	private TaskRunnerMapper taskRunnerMapper;
	@Autowired
	private TaskRunnerDetailMapper taskRunnerDetailMapper;
	
	//Finder
	@Autowired
	private TaskRunnerFinder taskRunnerFinder;
	
	//Other Module
	@Autowired
	private TaskService taskServiceImpl;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Override
	@Transactional
	public void save(TaskRunnerDTO taskRunnerDto) throws Exception{
		if(taskRunnerDto.getId() == null){
			taskRunnerMapper.insert(taskRunnerDto);
			for (TaskRunnerDetailDTO taskRunnerDetailDto : taskRunnerDto.getTaskRunnerDetailDto()) {
				TaskRunnerDetail taskRunnerDetail = modelMapper.map(taskRunnerDetailDto, TaskRunnerDetail.class);
				taskRunnerDetail.setTaskRunnerHdrId(taskRunnerDto.getId());
				taskRunnerDetail.setCreatedBy(taskRunnerDto.getCreatedBy());
				taskRunnerDetail.setCreationDate(taskRunnerDto.getCreationDate());
				taskRunnerDetail.setLastUpdateDate(taskRunnerDto.getLastUpdateDate());
				taskRunnerDetail.setLastUpdatedBy(taskRunnerDto.getLastUpdatedBy());
				taskRunnerDetailMapper.insert(taskRunnerDetail);
			}
		} else {
			taskRunnerMapper.updateByPrimaryKeySelective(taskRunnerDto);
			for (TaskRunnerDetailDTO taskRunnerDetailDto : taskRunnerDto.getTaskRunnerDetailDto()) {
				TaskRunnerDetail taskRunnerDetail = modelMapper.map(taskRunnerDetailDto, TaskRunnerDetail.class);
				taskRunnerDetail.setTaskRunnerHdrId(taskRunnerDto.getId());
				taskRunnerDetail.setCreatedBy(taskRunnerDto.getCreatedBy());
				taskRunnerDetail.setCreationDate(taskRunnerDto.getCreationDate());
				taskRunnerDetail.setLastUpdateDate(taskRunnerDto.getLastUpdateDate());
				taskRunnerDetail.setLastUpdatedBy(taskRunnerDto.getLastUpdatedBy());
				if(taskRunnerDetail.getId() != null)
					taskRunnerDetailMapper.updateByPrimaryKeySelective(taskRunnerDetail);
				else
					taskRunnerDetailMapper.insert(taskRunnerDetail);
			}
		}
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public TaskRunnerDTO getTaskRunnerById(Long id) {
		TaskRunnerDTO taskRunnerDto = new TaskRunnerDTO();
		TaskRunner taskRunner = taskRunnerMapper.selectByPrimaryKey(id);
		taskRunnerDto = modelMapper.map(taskRunner, TaskRunnerDTO.class);
		taskRunnerDto.setTaskRunnerDetailDto(getTaskRunnerDetailDtoByTaskRunnerId(taskRunnerDto.getId()));
		return taskRunnerDto;
	}

	@Override
	@Transactional(readOnly=true)
	public List<TaskRunner> getTaskRunnerByExample(TaskRunnerExample example, int limit, int offset) {	
		return taskRunnerMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int countTaskRunnerByExample(TaskRunnerExample example, Long taskGroupId) {
		return taskRunnerMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TaskRunnerDetail> getTaskRunnerDetailByTaskRunnerId(Long id) {
		TaskRunnerDetailExample example = new TaskRunnerDetailExample();
		example.createCriteria().andTaskRunnerHdrIdEqualTo(id);
		return taskRunnerDetailMapper.selectByExample(example);
		
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<TaskRunnerDetailDTO> getTaskRunnerDetailDtoByTaskRunnerId(Long id) {
		TaskRunnerDetailExample example = new TaskRunnerDetailExample();
		example.createCriteria().andTaskRunnerHdrIdEqualTo(id);
		List<TaskRunnerDetailDTO> returnList = new ArrayList<>();
		
		for(TaskRunnerDetail trd : taskRunnerDetailMapper.selectByExample(example)){
			TaskRunnerDetailDTO trdDto = new TaskRunnerDetailDTO();
			trdDto = modelMapper.map(trd, TaskRunnerDetailDTO.class);
			trdDto.setMainTask(taskServiceImpl.getTaskById(trdDto.getTaskId()));
			if(trdDto.getSccuessTaskId() != null){
				if(trdDto.getSccuessTaskId() != -1){
					trdDto.setSuccessTask(taskServiceImpl.getTaskById(trdDto.getSccuessTaskId()));
				}
				if(trdDto.getErrorTask()!= null){
					if(trdDto.getErrorTaskId() != -1){
						trdDto.setErrorTask(taskServiceImpl.getTaskById(trdDto.getErrorTaskId()));
					}
				}
			}
			returnList.add(trdDto);
		
		}
		
		return returnList;
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<TaskRunnerDTO> getTaskRunnerDtoByExample(TaskRunner example, int limit, int offset, Long taskGroupId) {
		return taskRunnerFinder.selectByExample(example, new RowBounds(offset, limit), taskGroupId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TaskRunner> getTaskRunnerByExample(TaskRunnerExample example) {
		return taskRunnerMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(TaskRunner example, Long taskGroupId) {
		return taskRunnerFinder.countByExample(example, taskGroupId);
	}

	@Override
	public void updateTaskRequestDetailByPrimaryKey(TaskRunnerDetail taskRunnerDetail) {
		taskRunnerDetailMapper.updateByPrimaryKey(taskRunnerDetail);
		
	}

	@Override
	public List<TaskRunnerDetail> getTaskRunnerDetailByExample(
			TaskRunnerDetailExample example) {
		return taskRunnerDetailMapper.selectByExample(example);
	}

	@Override
	public List<TaskRunnerDTO> getBatchByExample(TaskRunner example, int limit,
			int offset, Long taskGroupId) {
		return taskRunnerFinder.selectBatchByExample(example, new RowBounds(offset, limit), taskGroupId);
	}

	@Override
	public int countBatchByExample(TaskRunner example, Long taskGroupId) {
		return taskRunnerFinder.countBatchByExample(example, taskGroupId);
	}

}
