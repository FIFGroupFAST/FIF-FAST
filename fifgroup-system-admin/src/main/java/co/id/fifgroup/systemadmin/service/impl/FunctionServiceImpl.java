package co.id.fifgroup.systemadmin.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.systemadmin.dao.FunctionMapper;
import co.id.fifgroup.systemadmin.domain.Function;
import co.id.fifgroup.systemadmin.domain.FunctionExample;
import co.id.fifgroup.systemadmin.dto.FunctionDTO;
import co.id.fifgroup.systemadmin.finder.FunctionFinder;
import co.id.fifgroup.systemadmin.service.FunctionService;
import co.id.fifgroup.systemadmin.validation.FunctionValidator;

@Transactional
@Service("functionService")
public class FunctionServiceImpl implements FunctionService {
	
	@Autowired
	private FunctionMapper functionMapper;
	
	@Autowired
	private FunctionFinder functionFinder;
	
	@Autowired
	private FunctionValidator functionValidator;
	
	@Override
	@Transactional
	public void save(Function function) throws Exception{
		functionValidator.validate(function);
		if(function.getId() == null) {
			functionMapper.insert(function);
		} else {
			functionMapper.updateByPrimaryKeySelective(function);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Function> getFunctionByExample(FunctionExample example) {
		return functionMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Function> selectByExample(FunctionExample example, int limit, int offset) {
		return functionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<FunctionDTO> findByExample(FunctionDTO functionDTO, int limit, int offset) {
		return functionFinder.findByExample(functionDTO, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<FunctionDTO> findByExample(FunctionDTO functionDTO) {
		return functionFinder.findByExample(functionDTO);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Function getFunctionById(Long id) {
		return functionMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(FunctionExample example) {
		return functionMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Function> getFunctionName() {
		return functionFinder.getFunctionName();
	}

}
