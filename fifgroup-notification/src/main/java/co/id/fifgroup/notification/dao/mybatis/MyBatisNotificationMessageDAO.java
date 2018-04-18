package co.id.fifgroup.notification.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.notification.dao.NotificationMessageDAO;
import co.id.fifgroup.notification.dao.mybatis.mapper.NotificationMessageMapper;
import co.id.fifgroup.notification.domain.NotificationMessage;

public class MyBatisNotificationMessageDAO extends MyBatisDAOHelper implements
		NotificationMessageDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(MyBatisNotificationMessageDAO.class);

	@Override
	public void insertNewMessage(NotificationMessage notificationMessage)
			throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			mapper.insertNewMessage(notificationMessage);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public void updateMessageStatus(Long messageId, int messageStatus)
			throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("messageId", messageId);
			parameterMap.put("messageStatus", messageStatus);
			mapper.updateMessageStatus(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public List<NotificationMessage> getMessageByToId(UUID toId)
			throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("toId", toId);
			return mapper.getMessageByToId(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public int countUnreadMessage(UUID toId) throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("toId", toId);
			return mapper.countUnreadMessage(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public List<NotificationMessage> getMessageByToId(UUID toId, UUID jobId,
			UUID roleId) throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("toId", toId);
			parameterMap.put("jobId", jobId);
			parameterMap.put("roleId", roleId);
			return mapper.getMessageByToId(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public int countUnreadMessage(UUID toId, UUID jobId, UUID roleId)
			throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("toId", toId);
			parameterMap.put("jobId", jobId);
			parameterMap.put("roleId", roleId);
			return mapper.countUnreadMessage(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public List<NotificationMessage> getMessageByToId(UUID toId, int limit,
			int offset) throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("toId", toId);
			parameterMap.put("limit", limit);
			parameterMap.put("offset", offset);
			return mapper.getMessageByToIdPagging(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public int countAllMessage(UUID toId) throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("toId", toId);
			return mapper.countAllMessage(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public List<NotificationMessage> getAllNotificationMessage()
			throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			return mapper.getAllNotificationMessage();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public NotificationMessage getNotificationMessageByMessageId(Long messageId)
			throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("messageId", messageId);
			return mapper.getNotificationMessageByMessageId(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public void deleteMessage(Long messageId) throws Exception {
		try {
			NotificationMessageMapper mapper = sqlSession
					.getMapper(NotificationMessageMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("messageId", messageId);
			mapper.deleteNotificationMessageByMessageId(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

}
