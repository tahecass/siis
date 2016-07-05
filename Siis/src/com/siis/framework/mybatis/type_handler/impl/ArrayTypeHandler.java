/**
 * 
 */
package com.casewaresa.framework.mybatis.type_handler.impl;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.casewaresa.framework.mybatis.type_handler.TypeTableOracleHandler;

/**
 * 
 * 
 * @author JMORA @
 */
@MappedJdbcTypes(JdbcType.ARRAY)
public class ArrayTypeHandler extends TypeTableOracleHandler {

    public ArrayTypeHandler() {
	setSqlName("SECUENCIAS_TBL");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet
     * , java.lang.String)
     */
    public Object getNullableResult(ResultSet arg0, String arg1)
	    throws SQLException {
	log.debug("ArrayTypeHandler.getNullableResult()");
	log.trace("ResultSet ==> " + arg0);
	log.trace("String ==> " + arg1);
	Array array = arg0.getArray(arg1);
	ArrayList<Long> arrayList = new ArrayList<Long>();
	ResultSet rs = array.getResultSet();
	while (rs.next()) {
	    /*
	     * Se pide en el ResultSet del Array la posicion 2 ya que la 1 es el
	     * index del Array mas no el Valor
	     */
	    arrayList.add(rs.getLong(2));
	}
	return arrayList.toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet
     * , int)
     */
    public Object getNullableResult(ResultSet arg0, int arg1)
	    throws SQLException {
	log.debug("ArrayTypeHandler.getNullableResult()");
	log.trace("ResultSet ==> " + arg0);
	log.trace("int ==> " + arg1);
	Array array = arg0.getArray(arg1);
	ArrayList<Long> arrayList = new ArrayList<Long>();
	ResultSet rs = array.getResultSet();
	while (rs.next()) {
	    /*
	     * Se pide en el ResultSet del Array la posicion 2 ya que la 1 es el
	     * index del Array mas no el Valor
	     */
	    arrayList.add(rs.getLong(2));
	}
	return arrayList.toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.
     * CallableStatement, int)
     */
    public Object getNullableResult(CallableStatement arg0, int arg1)
	    throws SQLException {
	log.trace("getNullableResult()");
	log.trace("CallableStatement ==> " + arg0);
	log.trace("int ==> " + arg1);

	Array array = arg0.getArray(arg1);
	ArrayList<Long> arrayList = new ArrayList<Long>();
	ResultSet rs = array.getResultSet();
	while (rs.next()) {
	    /*
	     * Se pide en el ResultSet del Array la posicion 2 ya que la 1 es el
	     * index del Array mas no el Valor
	     */
	    arrayList.add(rs.getLong(2));
	}

	return arrayList.toArray();
    }

}
