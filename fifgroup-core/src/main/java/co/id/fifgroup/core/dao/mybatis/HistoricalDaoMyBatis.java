package co.id.fifgroup.core.dao.mybatis;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import co.id.fifgroup.core.util.DateUtil;

import co.id.fifgroup.core.dao.HistoricalDao;
import co.id.fifgroup.core.domain.HistoricalObject;

public class HistoricalDaoMyBatis<ID extends Serializable, T extends HistoricalObject>
		implements HistoricalDao<ID, T> {
	
	@Autowired
	protected SqlSession sqlSession;
	
	@Autowired
	protected StatementUtil statementUtil;
	
	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public HistoricalDaoMyBatis() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<T> findById(ID id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("effectiveDate", null);
		return sqlSession.selectList("find" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ById", params);
	}

	@Override
	public T findById(ID id, Date effectiveDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("effectiveDate", effectiveDate);
		
		List<T> result = sqlSession.selectList("find" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ById", params);

		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List<T> findByExample(T example, Date effectiveDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("example", example);
		params.put("effectiveDate", effectiveDate);
		return sqlSession.selectList("find" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ByExample", params);
	}

	@Override
	public List<T> findByExample(T example, Date effectiveDate, int offset,
			int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("example", example);
		params.put("effectiveDate", effectiveDate);
		return sqlSession.selectList("find" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ByExample",
				params, new RowBounds(offset, limit));
	}

	@Override
	public Integer countByExample(T example, Date effectiveDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("example", example);
		params.put("effectiveDate", effectiveDate);
		Integer result =  sqlSession.selectOne("count" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ByExample",
				params);
		return null != result ? result : 0;
	}

	@Override
	public void end(T entity, Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entity", entity);
		params.put("endDate", endDate);
		
		sqlSession.update("end" + StatementUtil.getClassNameWithoutPackage(persistentClass), params);

	}

	@Override
	public void create(T currentVersion, T newVersion) {
		sqlSession.insert("createNew" + StatementUtil.getClassNameWithoutPackage(persistentClass), newVersion);
		
		if (null != currentVersion)
			end(currentVersion, DateUtil.add(newVersion.getStartDate(), Calendar.DATE, -1));
	}

	@Override
	public void update(T entity) {
		sqlSession.update("update" + StatementUtil.getClassNameWithoutPackage(persistentClass), entity);
	}

}
