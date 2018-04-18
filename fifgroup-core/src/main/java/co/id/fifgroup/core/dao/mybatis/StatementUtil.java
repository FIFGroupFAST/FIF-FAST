package co.id.fifgroup.core.dao.mybatis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("statementUtil")
public class StatementUtil {

	protected Map<Class<?>, String> insertStatements = Collections
			.synchronizedMap(new HashMap<Class<?>, String>());
	protected Map<Class<?>, String> updateStatements = Collections
			.synchronizedMap(new HashMap<Class<?>, String>());
	protected Map<Class<?>, String> deleteStatements = Collections
			.synchronizedMap(new HashMap<Class<?>, String>());
	protected Map<Class<?>, String> selectStatements = Collections
			.synchronizedMap(new HashMap<Class<?>, String>());

	
	/**
	 * 
	 * @param persistentClass
	 * @return insert + persistentClass name
	 * ex : insertFunction
	 */
	public String getInsertStatement(Class<?> persistentClass) {
		return getStatement(persistentClass, insertStatements, "insert");
	}
	
	/**
	 * 
	 * @param persistentClass
	 * @return update + persistentClass name
	 * ex : updateFunction
	 */
	public String getUpdateStatement(Class<?> persistentClass) {
		return getStatement(persistentClass, updateStatements, "update");
	}
	
	/**
	 * 
	 * @param persistentClass
	 * @return delete + persistentClass name
	 * ex : deleteFunction
	 */
	public String getDeleteStatement(Class<?> persistentClass) {
		return getStatement(persistentClass, deleteStatements, "delete");
	}
	
	/**
	 * 
	 * @param persistentClass
	 * @return select + persistentClass name
	 * ex : selectFunction
	 */
	public String getSelectStatement(Class<?> persistentClass) {
		return getStatement(persistentClass, selectStatements, "select");
	}

	private String getStatement(Class<?> persistentClass,
			Map<Class<?>, String> cachedStatements, String prefix) {
		String statement = cachedStatements.get(persistentClass);
		if (statement != null) {
			return statement;
		}
		statement = prefix + getClassNameWithoutPackage(persistentClass);
		cachedStatements.put(persistentClass, statement);
		return statement;
	}

	protected static final Map<Class<?>, String> cachedNames = new HashMap<Class<?>, String>();

	public static String getClassNameWithoutPackage(Object object) {
		return getClassNameWithoutPackage(object.getClass());
	}

	public static String getClassNameWithoutPackage(Class<?> clazz) {
		String unqualifiedClassName = cachedNames.get(clazz);
		if (unqualifiedClassName == null) {
			String fullyQualifiedClassName = clazz.getName();
			unqualifiedClassName = fullyQualifiedClassName
					.substring(fullyQualifiedClassName.lastIndexOf('.') + 1);
			cachedNames.put(clazz, unqualifiedClassName);
		}
		return unqualifiedClassName;
	}
}
