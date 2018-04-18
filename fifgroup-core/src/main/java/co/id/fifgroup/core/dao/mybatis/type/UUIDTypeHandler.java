/**
 * Copyright (c) 2012 FIF. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of FIF.
 * ("Confidential Information"). <BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * FIF; other than in accordance with the written permission of FIF. <BR>
 * 
 * Created on: Aug 25, 2010
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */
package co.id.fifgroup.core.dao.mybatis.type;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import co.id.fifgroup.core.util.UUIDUtil;

/**
 * @author Dipo Makka
 * @version $Id$
 * @date $Date$
 *
 */
public class UUIDTypeHandler implements TypeHandler<UUID> {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, java.lang.String)
	 */
	@Override
	public UUID getResult(ResultSet rs, String columnName)
			throws SQLException {
		byte[] result = rs.getBytes(columnName);
		if(rs.wasNull()){
			return null;
		}
		return UUIDUtil.convertToUUID(result);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement, int)
	 */
	@Override
	public UUID getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		byte[] result = cs.getBytes(columnIndex);
		if(cs.wasNull()){
			return null;
		}
		return UUIDUtil.convertToUUID(result);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
	 */
	@Override
	public void setParameter(PreparedStatement ps, int i, UUID parameter,
			JdbcType jdbcType) throws SQLException {
		if(parameter == null){
			ps.setNull(i, Types.VARBINARY);
		} else{
			ps.setObject(i, UUIDUtil.convertToBytes(parameter), Types.VARBINARY);			
		}
	}

	@Override
	public UUID getResult(ResultSet arg0, int arg1) throws SQLException {
		byte[] result = arg0.getBytes(arg1);
		if(arg0.wasNull()){
			return null;
		}
		return UUIDUtil.convertToUUID(result);
	}

}
