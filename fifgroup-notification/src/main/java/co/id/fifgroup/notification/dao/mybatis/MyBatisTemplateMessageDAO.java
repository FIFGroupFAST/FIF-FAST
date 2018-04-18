package co.id.fifgroup.notification.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.notification.dao.TemplateMessageDAO;
import co.id.fifgroup.notification.dao.mybatis.mapper.TemplateMessageMapper;
import co.id.fifgroup.notification.domain.TemplateMessage;

public class MyBatisTemplateMessageDAO extends MyBatisDAOHelper implements TemplateMessageDAO {

	private static final Logger logger = LoggerFactory.getLogger(MyBatisTemplateMessageDAO.class);
	
	@Override
	public List<TemplateMessage> getAllTemplate() throws Exception {
		try{
			TemplateMessageMapper mapper = sqlSession.getMapper(TemplateMessageMapper.class);
			return mapper.getAllTemplate();
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}

	@Override
	public TemplateMessage getTemplateByName(String name)
			throws Exception {
		try{
			TemplateMessageMapper mapper = sqlSession.getMapper(TemplateMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("name", name);			
			return mapper.getTemplateByName(parameterMap);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}

	@Override
	public void insertNewTemplate(TemplateMessage templateMessage)
			throws Exception {
		try{
			TemplateMessageMapper mapper = sqlSession.getMapper(TemplateMessageMapper.class);
			mapper.insertNewTemplate(templateMessage);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		}
	}

	@Override
	public TemplateMessage getTemplateById(Long templateId)
			throws Exception {
		try{
			TemplateMessageMapper mapper = sqlSession.getMapper(TemplateMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("templateId", templateId);
			return mapper.getTemplateById(parameterMap);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		}
	}

	@Override
	public void updateTemplate(TemplateMessage templateMessage)
			throws Exception {
		try{
			TemplateMessageMapper mapper = sqlSession.getMapper(TemplateMessageMapper.class);
			mapper.updateTemplate(templateMessage);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		}
	}

	@Override
	public int countTemplateByName(String name) throws Exception {
		try{
			TemplateMessageMapper mapper = sqlSession.getMapper(TemplateMessageMapper.class);
			return mapper.countTemplateByName(name);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		}
	}

	@Override
	public TemplateMessage getTemplateByNameAndId(String templateName, Long templateId)
			throws Exception {
		try{
			TemplateMessageMapper mapper = sqlSession.getMapper(TemplateMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			String wrappedName = "%" + templateName + "%";
			parameterMap.put("templateId", templateId);
			parameterMap.put("templateName", wrappedName);
			return mapper.getTemplateByNameAndId(parameterMap);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		}
	}

}
