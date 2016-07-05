package com.casewaresa.framework.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class XMLTypeHandler implements TypeHandler<Object>{
	
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, (String) parameter);
	}

	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getString(columnName);
	}
	
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getString(columnIndex);
	}

	@Override
	public Object getResult(ResultSet arg0, int arg1) throws SQLException {
	    
	    return null;
	}
}
