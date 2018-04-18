package co.id.fifgroup.systemadmin.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.systemadmin.dao.TaskGroupDetailMapper;
import co.id.fifgroup.systemadmin.dao.TaskGroupMapper;
import co.id.fifgroup.systemadmin.domain.TaskGroup;
import co.id.fifgroup.systemadmin.domain.TaskGroupExample;
import co.id.fifgroup.systemadmin.dto.TaskGroupDTO;
import co.id.fifgroup.systemadmin.dto.TaskGroupDetailDTO;
import co.id.fifgroup.systemadmin.finder.TaskGroupFinder;
import co.id.fifgroup.systemadmin.service.TaskGroupService;
import co.id.fifgroup.systemadmin.validation.TaskGroupValidator;

@Transactional
@Service("taskGroupServiceImpl")
public class TaskGroupServiceImpl implements TaskGroupService {
	
	//Task Group Entity
	@Autowired
	private TaskGroupMapper taskGroupMapper;
	@Autowired
	private TaskGroupDetailMapper taskGroupDetailMapper;
	
	//Task Group Finder
	@Autowired
	private TaskGroupFinder taskGroupFinder;
	
	//Validation
	@Autowired
	private TaskGroupValidator taskGroupValidator;
	
	@Override
	@Transactional(readOnly=true)
	public List<TaskGroup> getTaskGroupByExample(TaskGroupExample taskGroupExample, int limit, int offset) {
		taskGroupExample.setOrderByClause("UPPER(TASK_GROUP_NAME) ASC");
		return taskGroupMapper.selectByExampleWithRowbounds(taskGroupExample, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<TaskGroupDTO> getTaskGroupByExampleByRowBounds(TaskGroup taskGroup, int limit, int offset) {
		return taskGroupFinder.findByExampleWithRowbounds(taskGroup, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public List<TaskGroupDetailDTO> getTaskGroupIdAndRunnerName(Long id) {
		return taskGroupFinder.getTaskGroupIdAndRunnerName(id);
	}

	@Override
	@Transactional
	public void save(TaskGroupDTO taskGroupDto) throws Exception{
		taskGroupValidator.validate(taskGroupDto);
		if(taskGroupDto.getId() == null) {
			taskGroupMapper.insert(taskGroupDto);		
			for (TaskGroupDetailDTO taskGroupDetailDto : taskGroupDto.getTaskGroupDetailDto()) {
				taskGroupDetailDto.setTaskGroupId(taskGroupDto.getId());
				taskGroupDetailDto.setCreatedBy(taskGroupDto.getCreatedBy());
				taskGroupDetailDto.setCreationDate(taskGroupDto.getCreationDate());
				taskGroupDetailDto.setLastUpdatedBy(taskGroupDto.getLastUpdatedBy());
				taskGroupDetailDto.setLastUpdateDate(taskGroupDto.getLastUpdateDate());
				taskGroupDetailMapper.insert(taskGroupDetailDto);
			}
		} else {
			taskGroupMapper.updateByPrimaryKey(taskGroupDto);
			
			taskGroupFinder.deleteByTaskGroupId(taskGroupDto.getId());
			for (TaskGroupDetailDTO taskGroupDetailDto : taskGroupDto.getTaskGroupDetailDto()) {
				taskGroupDetailDto.setTaskGroupId(taskGroupDto.getId());
				taskGroupDetailDto.setCreatedBy(taskGroupDto.getCreatedBy());
				taskGroupDetailDto.setCreationDate(taskGroupDto.getCreationDate());
				taskGroupDetailDto.setLastUpdatedBy(taskGroupDto.getLastUpdatedBy());
				taskGroupDetailDto.setLastUpdateDate(taskGroupDto.getLastUpdateDate());
				taskGroupDetailMapper.insert(taskGroupDetailDto);
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public TaskGroup getTaskGroupById(Long id) {
		return taskGroupMapper.selectByPrimaryKey(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public int countByExample(TaskGroupExample example) {
		return taskGroupMapper.countByExample(example);
	}

}
