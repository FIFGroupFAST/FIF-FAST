package co.id.fifgroup.systemadmin.service.impl;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.systemadmin.dao.ExecutableFileMapper;
import co.id.fifgroup.systemadmin.dao.TaskMapper;
import co.id.fifgroup.systemadmin.dao.TaskParameterMapper;
import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.domain.ExecutableFileExample;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.domain.TaskExample;
import co.id.fifgroup.systemadmin.domain.TaskParameter;
import co.id.fifgroup.systemadmin.domain.TaskParameterExample;
import co.id.fifgroup.systemadmin.dto.ExecutableFileDTO;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;
import co.id.fifgroup.systemadmin.finder.TaskFinder;
import co.id.fifgroup.systemadmin.service.TaskService;
import co.id.fifgroup.systemadmin.validation.TaskFileUploadValidator;
import co.id.fifgroup.systemadmin.validation.TaskValidator;

@Transactional
@Service
public class TaskServiceImpl implements TaskService{
	
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	
	//Task Entity
	@Autowired
	private ExecutableFileMapper executableFileMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private TaskParameterMapper taskParameterMapper;
	
	//Task Finder
	@Autowired
	private TaskFinder taskFinder;
	
	//Validation
	@Autowired
	private TaskFileUploadValidator taskFileUploadValidator;
	@Autowired
	private TaskValidator taskValidator;
	
	//OtherModule
	@Autowired
	private ModuleService moduleServiceImpl;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private SecurityService securityServiceImpl;
	
	
	@Override
	@Transactional
	public void save(TaskDTO taskDto) throws Exception{
		taskValidator.validate(taskDto);
		if(taskDto.getId() == null){
			taskMapper.insert(taskDto);
			if(taskDto.getTaskParameters() != null){
				for (TaskParameter taskParameter : taskDto.getTaskParameters()) {
					taskParameter.setTaskId(taskDto.getId());
					taskParameter.setCreatedBy(taskDto.getCreatedBy());
					taskParameter.setCreationDate(taskDto.getCreationDate());
					taskParameter.setLastUpdateDate(taskDto.getLastUpdateDate());
					taskParameter.setLastUpdatedBy(taskDto.getLastUpdatedBy());
					taskParameterMapper.insert(taskParameter);
				}
			}
			
		} else {
			taskMapper.updateByPrimaryKey(taskDto);
			TaskParameterExample taskParameterExample = new TaskParameterExample();
			taskParameterExample.createCriteria()
			.andTaskIdEqualTo(taskDto.getId());
			for(TaskParameter tp :taskParameterMapper.selectByExample(taskParameterExample)){
				taskParameterMapper.deleteByPrimaryKey(tp.getId());
			}
			
			if(taskDto.getTaskParameters() != null){
				for (TaskParameter taskParameter : taskDto.getTaskParameters()) {
					taskParameter.setId(null);
					taskParameter.setCreatedBy(taskDto.getCreatedBy());
					taskParameter.setCreationDate(taskDto.getCreationDate());
					taskParameter.setLastUpdateDate(taskDto.getLastUpdateDate());
					taskParameter.setLastUpdatedBy(taskDto.getLastUpdatedBy());
					if(taskDto.isHasStaticParams()){
						if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_BRANCH.toString()) 
								|| taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString()) 
								|| taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_WITHOUT_POS.toString()))
							taskParameter.setStaticParam(true);
					}
						
					if(taskParameter.getId() != null) {
						taskParameterMapper.updateByPrimaryKey(taskParameter);
					} else {
						taskParameter.setTaskId(taskDto.getId());
						taskParameterMapper.insert(taskParameter);
					}
				}
			}
			
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ExecutableFile> getExecutableFileByExecutableFileName(String name){
		ExecutableFileExample example = new ExecutableFileExample();
		example.createCriteria().andExecutableFileNameLikeInsensitive(name);
		return executableFileMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Task> getTaskByExample(TaskExample example, int limit, int offset) {
		example.setOrderByClause("UPPER(TASK_NAME) ASC");
		return taskMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public TaskDTO getTaskById(Long id) {
		TaskDTO taskDto = new TaskDTO();
		
		Task task = taskMapper.selectByPrimaryKey(id);
		if(task != null){
			taskDto = modelMapper.map(task, TaskDTO.class);
			taskDto.setTaskParameters(getTaskParameterDtoByTaskId(id));
			taskDto.setExecutableFile(executableFileMapper.selectByPrimaryKey(taskDto.getExecutableFileId()));
		}
		
		return taskDto;
	}
	
	public List<TaskParameterDTO> getTaskParameterDtoByTaskId(Long id) {
		/*TaskParameterExample example = new TaskParameterExample();
		example.createCriteria().andTaskIdEqualTo(id);
		List<TaskParameter> list = taskParameterMapper.selectByExample(example);
		List<TaskParameterDTO> returnValue = new ArrayList<>();
		for (TaskParameter taskRunnerDetail : list) {
			TaskParameterDTO trdDto = new TaskParameterDTO();
			trdDto = modelMapper.map(taskRunnerDetail, TaskParameterDTO.class);
			returnValue.add(trdDto);
		}
		return returnValue;*/
		return taskFinder.getTaskParametersByTaskId(id);
	}


	@Override
	@Transactional(readOnly=true)
	public List<ExecutableFile> getExecutableFileByExample(ExecutableFileDTO example,  int offset, int limit) {
		return taskFinder.selectExecutableFileByExample(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(TaskExample example) {
		return taskMapper.countByExample(example);
	}
	
	@Override
	@Transactional
	public void uploadExecutableFile(ExecutableFile executableFile, InputStream inputStream) throws Exception {
		taskFileUploadValidator.validate(executableFile);
//		uploadFile(getModuleIdByModuleName(executableFile.getModuleId()), executableFile.getExecutableFileName(), inputStream);
		executableFileMapper.insert(executableFile);
	}
	
	private String getModuleIdByModuleName(Long moduleId){
		List<Module> modules = moduleServiceImpl.getModuleByExample(null);
		String returnValue = "";
		for(Module x: modules){
			if(x.getModuleId()==moduleId){
				returnValue= x.getModuleName();
			}
		}
		return returnValue;
	}
	
	@Override
	public void uploadFile(String moduleName, String fileName, InputStream inputStream) {
		DataOutputStream out = null;
		try {
			String filePath = Labels.getLabel("root.folder") +"\\"+ moduleName;
			fileName = filePath + "\\" + fileName;
			File fileDir = new File(filePath);
			File file = new File(fileName);
			if (fileDir.isDirectory()) {
				out = new DataOutputStream(new BufferedOutputStream(
						new FileOutputStream(file)));
			} else {
				fileDir.mkdir();
				out = new DataOutputStream(new BufferedOutputStream(
						new FileOutputStream(file)));
			}

			int ch = 0;
			while ((ch = inputStream.read()) != -1) {
				out.write(ch);
			}
			out.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	@Transactional
	public void uploadExecutableFile(ExecutableFile executableFile){
		executableFileMapper.insert(executableFile);
	}

	@Override
	public void validateExecutableFile(ExecutableFile executableFile) throws ValidationException {
		taskFileUploadValidator.validate(executableFile);
	}

	@Override
	@Transactional(readOnly=true)
	public Task getEndOfTask() {
		return taskMapper.selectByPrimaryKey(-1L);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TaskDTO> findTaskByExample(TaskDTO taskDTO, int limit, int offset) {
		return taskFinder.findTaskByExample(taskDTO, new RowBounds(offset, limit));
	}

	@Override
	public int countTaskParameterByExample(TaskParameterExample example) {
		return taskParameterMapper.countByExample(example);
	}

	@Override
	public List<TaskParameter> getTaskParameterByExample(TaskParameterExample example) {
		return taskParameterMapper.selectByExample(example);
	}

	@Override
	public List<TaskParameterDTO> getTaskParametersByTaskId(Long id) {
		return taskFinder.getTaskParametersByTaskId(id);
	}

	@Override
	public Task getTaskByPrimaryKey(Long id) {
		return taskMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateExecutableFile(ExecutableFile executableFile) {
		ExecutableFileExample example = new ExecutableFileExample();
		example.createCriteria()
		.andExecutableFileNameEqualTo(executableFile.getExecutableFileName());
		executableFile  = executableFileMapper.selectByExample(example).get(0);
		executableFile.setLastUpdateDate(new Date());
		executableFile.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		executableFileMapper.updateByPrimaryKey(executableFile);
		
	}
}
