package com.casewaresa.framework.mybatis.type_handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.log4j.Logger;

public class TypeTableOracleHandler extends BaseTypeHandler<Object> {

    protected static Logger log = Logger
	    .getLogger(TypeTableOracleHandler.class);

    private String sqlName = "";

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet
     * , java.lang.String)
     */
    @Override
    public Object getNullableResult(ResultSet arg0, String arg1)
	    throws SQLException {

	try {
	    throw new Exception(
		    " [ Error ] - Método   getNullableResult(ResultSet arg0, String arg1) en  "
			    + this.getClass().getName() + "No implementado");
	} catch (Exception e) {

	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet
     * , int)
     */
    @Override
    public Object getNullableResult(ResultSet arg0, int arg1)
	    throws SQLException {
	try {
	    throw new Exception(
		    " [ Error ] - Método  getNullableResult(ResultSet arg0, int arg1) en "
			    + this.getClass().getName() + "No implementado");
	} catch (Exception e) {

	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.
     * CallableStatement, int)
     */
    @Override
    public Object getNullableResult(CallableStatement arg0, int arg1)
	    throws SQLException {
	try {
	    throw new Exception(
		    " [ Error ] - Método  getNullableResult(CallableStatement arg0, int arg1)F en "
			    + this.getClass().getName() + "No implementado");
	} catch (Exception e) {

	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.ibatis.type.BaseTypeHandler#setNonNullParameter(java.sql.
     * PreparedStatement, int, java.lang.Object,
     * org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setNonNullParameter(PreparedStatement arg0, int arg1,
	    Object arg2, JdbcType arg3) throws SQLException {
	log.trace("ArrayTypeHandler.setNonNullParameter()");
	log.trace("CallableStatement ==> " + arg0);
	log.trace("int ==> " + arg1);
	log.trace("Object ==> " + arg2);
	log.trace("JdbcType ==> " + arg3);
	/*
	 * Se optiene un Descriptor del Tipo de Dato a Crear para el caso del
	 * sqlName que es un TYPE
	 */
	ArrayDescriptor desc = ArrayDescriptor.createDescriptor(sqlName,
		arg0.getConnection());
	/*
	 * Se crea el ARRAY de Tipo Oracle con el Descriptor anteriormente
	 * Creado
	 */
	if (arg2 != null) {
	    ARRAY oracleArray = new ARRAY(desc, arg0.getConnection(), arg2);
	    /*
	     * Se envia el parametro al PreparedStatement con el ARRAY definido
	     * por el Descriptor
	     */
	    arg0.setArray(arg1, oracleArray);
	} else {
	    arg0.setNull(arg1, java.sql.Types.ARRAY);
	}

    }

    public String getSqlName() {
	return sqlName;
    }

    public void setSqlName(String sqlName) {
	this.sqlName = sqlName;
    }

}
