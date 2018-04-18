package co.id.fifgroup.core.dao.mybatis;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import co.id.fifgroup.core.dao.GenericDao;
import co.id.fifgroup.core.domain.DomainObject;

public class GenericDaoMybatis<ID extends Serializable, T extends DomainObject> implements GenericDao<ID, T> {

	@Autowired
	protected SqlSession sqlSession;
	
	@Autowired
	protected StatementUtil statementUtil;
	
	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public GenericDaoMybatis() {
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
	public List<T> findAll() {
		return sqlSession.selectList("findAll" + StatementUtil.getClassNameWithoutPackage(persistentClass));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		return (T) sqlSession.selectOne("find" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ById", id);
	}

	@Override
	public void insert(T entity) {
		entity.setObjectVersion(0);
		sqlSession.insert(statementUtil.getInsertStatement(persistentClass), entity);
	}

	@Override
	public void update(T entity) {
		int result = sqlSession.insert(statementUtil.getUpdateStatement(persistentClass), entity);
		if( result > 0)
			entity.setObjectVersion(entity.getObjectVersion() + 1);
	}
	
	@Override
	public void delete(T entity) {
		sqlSession.delete(statementUtil.getDeleteStatement(persistentClass), entity);
	}

	@Override
	public List<T> findByExample(T entity) {
		return sqlSession.selectList("find" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ByExample", entity);
	}

	@Override
	public int countByExample(T entity) {
		Integer result = sqlSession.selectOne("count" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ByExample", entity);
		return null == result ? 0 : result;
	}

	@Override
	public List<T> findByExample(T entity, int offset, int limit) {
		return sqlSession.selectList("find" + StatementUtil.getClassNameWithoutPackage(persistentClass) + "ByExample", entity, new RowBounds(offset, limit));
	}

}
