package com.siis.framework.dao;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.jdom.JDOMException;

import com.siis.framework.Filter.anottations.Column;
import com.siis.framework.Filter.anottations.Mapper;
import com.siis.framework.Filter.anottations.Relation;
import com.siis.framework.Filter.anottations.RelationFilter;
import com.siis.framework.Filter.dto.ColumnsMapper;
import com.siis.framework.Filter.manager.DTOMapper;
import com.siis.framework.dao.impl.DaoStandard;
import com.siis.framework.dto.IBeanAbstracto;

@SuppressWarnings("unchecked")
public class ParametrizacionDao extends DaoStandard {

    private SqlSession session = sqlSessionFactory.openSession(true);

    public Collection<Object> obtenerListado(String sqlName) throws Exception {
	try {
	    return session.selectList(sqlName);
	} finally {
	    session.close();
	}
    }

    public Collection<Object> obtenerListado(String sqlName, Object object)
	    throws Exception {
	try {
		log.info("ParametrizacionDAO][Collection][49] sqlName: "+sqlName);
	    return session.selectList(sqlName, object);
	} finally {
	    session.close();
	}
    }

    @Override
    public Object obtenerRegistro(String sqlName) throws Exception {
	try {
	    return session.selectOne(sqlName);
	} finally {
	    session.close();
	}
    }

    @Override
    public Object obtenerRegistro(String sqlName, Object object)
	    throws Exception {
	try {
	    return session.selectOne(sqlName, object);
	} finally {
	    session.close();
	}
    }

    @Override
    public Object ejecutarProcedimiento(String sqlName) throws Exception {
	try {
	    return session.update(sqlName);
	} finally {
	    session.close();
	}
    }

    @Override
    public Object ejecutarProcedimiento(String sqlName, Object object)
	    throws Exception {
	try {
	    return session.update(sqlName, object);
	} finally {
	    session.close();
	}
    }

    @Override
    public Object insertarRegistro(String sqlName, Object object)
	    throws Exception {
	try {
	    return session.insert(sqlName, object);
	} finally {
	    session.close();
	}
    }

    @Override
    public Object actualizarRegistro(String sqlName, Object object)
	    throws Exception {
	try {
	    return session.update(sqlName, object);
	} finally {
	    session.close();
	}
    }

    @Override
    public Object borrarRegistro(String sqlName, Object object)
	    throws Exception {
	try {
	    return session.delete(sqlName, object);
	} finally {
	    session.close();
	}
    }

    public Object validarSQL(String sql) throws Exception {
	try {
	    Connection con = session.getConnection();
	    return con.prepareStatement(sql);
	} finally {
	    session.close();
	}
    }

    /**
     * 
     * @param tipoClase
     * @param sql
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ParseException
     * @throws Exception
     */
    public Object obtenerDinamico(Object tipoClase, String sql)
	    throws SQLException, InterruptedException, NoSuchMethodException,
	    IllegalAccessException, InvocationTargetException,
	    InstantiationException, ParseException, Exception {
	Object clase = null;

	List<Object> listaDatos = listadoDinamico(tipoClase, sql);
	if (listaDatos.size() != 0)
	    clase = listaDatos.get(0);

	return clase;
    }

    /**
     * Modifica los atributos dinamicamente del objetos enviado por par�metro
     * mediante el resultado de la consulta sql
     * 
     * @param <T>
     * @param tipoClase
     * @param sql
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ParseException
     * @throws Exception
     */
    public <T> List<T> listadoDinamico(Object tipoClase, String sql)
	    throws SQLException, InterruptedException, NoSuchMethodException,
	    IllegalAccessException, InvocationTargetException,
	    InstantiationException, ParseException, Exception {
	// lista de retorno
	List<T> listaDatos = new LinkedList<T>();

	try {
	    // estas tres lineas crean la coneccion, preparan la sentencia y
	    // ejcutan la consulta.

	    Map<String, Object> parametros = new HashMap<String, Object>();

	    parametros.put("SQL_DINAMICA", sql);

	    Collection<Object> collection = obtenerListado("UTIL_sqlDinamico",
		    parametros);

	    // Se obtiene el tipor de calse del objeto enviado por par�metro
	    Class<?> clase = Class.forName(tipoClase.getClass().getName());
	    // Se obtiene el mapper configurado en la clase
	    Mapper maper = (Mapper) clase.getAnnotation(Mapper.class);
	    // Se obtiene el id del mapper configurado en la clase
	    String id = maper.id();

	    // Se recorre el resultado de la consulta
	    for (Object objeto : collection) {
		Map<String, Object> mapaRow = (Map<String, Object>) objeto;

		List<ColumnsMapper> columnsMappers = new LinkedList<ColumnsMapper>();
		// Se crea una nueva intancia del objeto recibido por
		// par�rametros.
		Object objetoRetorno = tipoClase.getClass().newInstance();
		try {
		    // Se obtienen los field declarados en la clase mapeada
		    for (Field field : DTOMapper.getInstance()
			    .getMapperReport(id).getClazz().getDeclaredFields()) {
			// columnas simples que corresponden a tipos de datos
			// primitivos como Long,String, Integer, etc.
			if (field.isAnnotationPresent(Column.class)) {
			    Column column = field.getAnnotation(Column.class);
			    columnsMappers.add(new ColumnsMapper(column.name(),
				    column.label(), column.type(), column
					    .property(), column.index()));
			    // Se valida que exista la columna en el ResultSet
			    if (findColumn(mapaRow, column.name()) >= 0) {
				// se obtiene el valor de la columna en el
				// ResultSet
				Object valor = mapaRow.get(column.name());
				// se modifica el atributo de la clase
				getObjetoModificado(objetoRetorno.getClass(),
					column, objetoRetorno,
					column.property(), valor);
			    }

			    // columnas relacionales Corresponden al segundo
			    // nivel.
			} else if (field.isAnnotationPresent(Relation.class)) {
			    Relation relation = field
				    .getAnnotation(Relation.class);
			    Class<?> clazzField = DTOMapper
				    .getInstance()
				    .getMapperReport(
					    relation.idMapperForRelation())
				    .getClazz();
			    Object objetoRelacionadoSdoNivel = null;
			    String[] filedsRelation = relation.fields();
			    int j = 0;
			    for (int i = 0; i < filedsRelation.length; i++) {
				Field fAux = clazzField
					.getDeclaredField(filedsRelation[i]);
				Column cr = fAux.getAnnotation(Column.class);
				// Se valida que exista la columna en el
				// ResultSet
				if (findColumn(mapaRow,
					relation.columnsName()[i]) >= 0) {
				    // se obtiene el valor de la columna en el
				    // ResultSet
				    Object valor = mapaRow.get(relation
					    .columnsName()[i]);
				    // se valida que el objeto por lo menes
				    // tenga un atributo a modificar.
				    if (valor != null && j == 0) {
					objetoRelacionadoSdoNivel = clazzField
						.newInstance();
					j++;
				    }
				    if (objetoRelacionadoSdoNivel != null)
					// se modifica el atributo de la clase
					getObjetoModificado(clazzField, cr,
						objetoRelacionadoSdoNivel,
						fAux.getName(), valor);
				}

			    }
			    // Se valida que objetoRelacionadoSdoNivel NO se
			    // null para asignarcelo al objeto del primer nivel
			    if (objetoRelacionadoSdoNivel != null) {
				Class<?>[] ListParametros = new Class[1];
				ListParametros[0] = clazzField;
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoSdoNivel
				Method metodoSetDto = this.getMethodSet(
					objetoRetorno.getClass(),
					field.getName(), ListParametros);
				// si el metodo ex�ste se invoca modificando el
				// objeto de primer nivel.
				if (metodoSetDto != null) {
				    metodoSetDto.invoke(objetoRetorno,
					    objetoRelacionadoSdoNivel);
				}
			    }
			    // columnas relacionales Corresponden al tercer
			    // nivel.
			} else if (field
				.isAnnotationPresent(RelationFilter.class)) {
			    RelationFilter relation = field
				    .getAnnotation(RelationFilter.class);
			    Class<?> clazzField = DTOMapper
				    .getInstance()
				    .getMapperReport(
					    relation.idMapperForRelation())
				    .getClazz();
			    String[] filedsRelation = relation.fields();
			    Object objetoRelacionadoSdoNivel = field.getType()
				    .newInstance();
			    Object objetoRelacionadoTcerNivel = null;
			    int j = 0;
			    for (int i = 0; i < filedsRelation.length; i++) {
				Field fAux = clazzField
					.getDeclaredField(filedsRelation[i]);
				Column cr = fAux.getAnnotation(Column.class);
				if (findColumn(mapaRow,
					relation.columnsName()[i]) >= 0) {
				    // se obtiene el valor de la columna en el
				    // ResultSet
				    Object valor = mapaRow.get(relation
					    .columnsName()[i]);
				    // se valida que el objeto por lo menes
				    // tenga un atributo a modificar.
				    if (valor != null && j == 0) {
					objetoRelacionadoTcerNivel = clazzField
						.newInstance();
					j++;
				    }
				    if (objetoRelacionadoTcerNivel != null)
					// se modifica el atributo de la clase
					getObjetoModificado(clazzField, cr,
						objetoRelacionadoTcerNivel,
						fAux.getName(), valor);
				}

			    }
			    // Se valida que objetoRelacionadoTcerNivel NO se
			    // null para asignarcelo al objeto del segundo nivel
			    if (objetoRelacionadoTcerNivel != null) {
				Class<?>[] ListParametrosSdoNivel = new Class[1];
				ListParametrosSdoNivel[0] = clazzField;
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoTcerNivel
				Method metodoSetDtoSdoNivel = this
					.getMethodSet(field.getType(),
						relation.property(),
						ListParametrosSdoNivel);
				// si el metodo ex�ste se invoca modificando el
				// objeto de segundo nivel
				if (metodoSetDtoSdoNivel != null) {
				    metodoSetDtoSdoNivel.invoke(
					    objetoRelacionadoSdoNivel,
					    objetoRelacionadoTcerNivel);
				}
			    }
			    // Se valida que objetoRelacionadoSdoNivel NO se
			    // null para asignarcelo al objeto del primer nivel
			    if (objetoRelacionadoSdoNivel != null) {
				Class<?>[] ListParametros = new Class[1];
				ListParametros[0] = field.getType();
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoSdoNivel
				Method metodoSetDtoPmerNivel = this
					.getMethodSet(objetoRetorno.getClass(),
						field.getName(), ListParametros);
				// si el metodo ex�ste se invoca modificando el
				// objeto de primer nivel
				if (metodoSetDtoPmerNivel != null) {
				    metodoSetDtoPmerNivel.invoke(objetoRetorno,
					    objetoRelacionadoSdoNivel);
				}
			    }

			}
		    }
		} catch (SecurityException e) {

		    log.error(e.getMessage(), e);
		} catch (JDOMException e) {

		    log.error(e.getMessage(), e);
		} catch (IOException e) {

		    log.error(e.getMessage(), e);
		} catch (NoSuchFieldException e) {

		    log.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {

		    log.error(e.getMessage(), e);
		}

		listaDatos.add((T) objetoRetorno);

	    }

	} catch (ClassNotFoundException e) {

	    log.error(e.getMessage(), e);
	} finally {
	    session.close();
	}

	return listaDatos;
    }

    
    /**
     * 
     * @param <T>
     * @param tipoClase
     * @param sql
     * @param parametros
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ParseException
     * @throws Exception
     */
    public <T> List<T> listadoDinamico(Object tipoClase, String sql,
	    Map<String, Object> parametros) throws SQLException,
	    InterruptedException, NoSuchMethodException,
	    IllegalAccessException, InvocationTargetException,
	    InstantiationException, ParseException, Exception {
	// lista de retorno
	List<T> listaDatos = new LinkedList<T>();

	try {
	    // estas tres lineas crean la coneccion, preparan la sentencia y
	    // ejcutan la consulta.
	    String sqlResult = sql;
	    IBeanAbstracto beanAbstracto = (IBeanAbstracto) parametros
		    .get("OBJETO");

	    log.info("OBJETO beanAbstracto =====> " + beanAbstracto != null ? "["
		    + beanAbstracto.getCodigo()
		    + "] "
		    + beanAbstracto.getNombre()
		    : "NULL beanAbstracto OBJETO");

	    if (beanAbstracto.getCodigo() != null
		    && beanAbstracto.getNombre() != null) {
		sqlResult = getSqlDinamico(sql, beanAbstracto.getCodigo(),
			beanAbstracto.getNombre());
	    } else if (beanAbstracto.getCodigo() != null) {
		sqlResult = getSqlDinamico(sql, beanAbstracto.getCodigo(), null);
	    } else if (beanAbstracto.getNombre() != null) {
		sqlResult = getSqlDinamico(sql, beanAbstracto.getNombre(), null);
	    }

	    log.info("SQL Entrada =====> " + sqlResult);

	    parametros.put("SQL_DINAMICA", sqlResult);

	    Collection<Object> collection = obtenerListado("UTIL_sqlDinamico",
		    parametros);

	    // Se obtiene el tipor de clase del objeto enviado por par�metro
	    Class<?> clase = Class.forName(tipoClase.getClass().getName());
	    // Se obtiene el mapper configurado en la clase
	    Mapper maper = (Mapper) clase.getAnnotation(Mapper.class);
	    // Se obtiene el id del mapper configurado en la clase
	    String id = maper.id();

	    // Se recorre el resultado de la consulta
	    for (Object objeto : collection) {
		Map<String, Object> mapaRow = (Map<String, Object>) objeto;

		List<ColumnsMapper> columnsMappers = new LinkedList<ColumnsMapper>();
		// Se crea una nueva intancia del objeto recibido por
		// par�rametros.
		Object objetoRetorno = tipoClase.getClass().newInstance();
		try {
		    // Se obtienen los field declarados en la clase mapeada
		    for (Field field : DTOMapper.getInstance()
			    .getMapperReport(id).getClazz().getDeclaredFields()) {
			// columnas simples que corresponden a tipos de datos
			// primitivos como Long,String, Integer, etc.
			if (field.isAnnotationPresent(Column.class)) {
			    Column column = field.getAnnotation(Column.class);
			    columnsMappers.add(new ColumnsMapper(column.name(),
				    column.label(), column.type(), column
					    .property(), column.index()));
			    // Se valida que exista la columna en el ResultSet
			    if (findColumn(mapaRow, column.name()) >= 0) {
				// se obtiene el valor de la columna en el
				// ResultSet
				Object valor = mapaRow.get(column.name());
				// se modifica el atributo de la clase
				getObjetoModificado(objetoRetorno.getClass(),
					column, objetoRetorno,
					column.property(), valor);
			    }

			    // columnas relacionales Corresponden al segundo
			    // nivel.
			} else if (field.isAnnotationPresent(Relation.class)) {
			    Relation relation = field
				    .getAnnotation(Relation.class);
			    Class<?> clazzField = DTOMapper
				    .getInstance()
				    .getMapperReport(
					    relation.idMapperForRelation())
				    .getClazz();
			    Object objetoRelacionadoSdoNivel = null;
			    String[] filedsRelation = relation.fields();
			    int j = 0;
			    for (int i = 0; i < filedsRelation.length; i++) {
				Field fAux = clazzField
					.getDeclaredField(filedsRelation[i]);
				Column cr = fAux.getAnnotation(Column.class);
				// Se valida que exista la columna en el
				// ResultSet
				if (findColumn(mapaRow,
					relation.columnsName()[i]) >= 0) {
				    // se obtiene el valor de la columna en el
				    // ResultSet
				    Object valor = mapaRow.get(relation
					    .columnsName()[i]);
				    // se valida que el objeto por lo menes
				    // tenga un atributo a modificar.
				    if (valor != null && j == 0) {
					objetoRelacionadoSdoNivel = clazzField
						.newInstance();
					j++;
				    }
				    if (objetoRelacionadoSdoNivel != null)
					// se modifica el atributo de la clase
					getObjetoModificado(clazzField, cr,
						objetoRelacionadoSdoNivel,
						fAux.getName(), valor);
				}

			    }
			    // Se valida que objetoRelacionadoSdoNivel NO se
			    // null para asignarcelo al objeto del primer nivel
			    if (objetoRelacionadoSdoNivel != null) {
				Class<?>[] ListParametros = new Class[1];
				ListParametros[0] = clazzField;
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoSdoNivel
				Method metodoSetDto = this.getMethodSet(
					objetoRetorno.getClass(),
					field.getName(), ListParametros);
				// si el metodo ex�ste se invoca modificando el
				// objeto de primer nivel.
				if (metodoSetDto != null) {
				    metodoSetDto.invoke(objetoRetorno,
					    objetoRelacionadoSdoNivel);
				}
			    }
			    // columnas relacionales Corresponden al tercer
			    // nivel.
			} else if (field
				.isAnnotationPresent(RelationFilter.class)) {
			    RelationFilter relation = field
				    .getAnnotation(RelationFilter.class);
			    Class<?> clazzField = DTOMapper
				    .getInstance()
				    .getMapperReport(
					    relation.idMapperForRelation())
				    .getClazz();
			    String[] filedsRelation = relation.fields();
			    Object objetoRelacionadoSdoNivel = field.getType()
				    .newInstance();
			    Object objetoRelacionadoTcerNivel = null;
			    int j = 0;
			    for (int i = 0; i < filedsRelation.length; i++) {
				Field fAux = clazzField
					.getDeclaredField(filedsRelation[i]);
				Column cr = fAux.getAnnotation(Column.class);
				if (findColumn(mapaRow,
					relation.columnsName()[i]) >= 0) {
				    // se obtiene el valor de la columna en el
				    // ResultSet
				    Object valor = mapaRow.get(relation
					    .columnsName()[i]);
				    // se valida que el objeto por lo menes
				    // tenga un atributo a modificar.
				    if (valor != null && j == 0) {
					objetoRelacionadoTcerNivel = clazzField
						.newInstance();
					j++;
				    }
				    if (objetoRelacionadoTcerNivel != null)
					// se modifica el atributo de la clase
					getObjetoModificado(clazzField, cr,
						objetoRelacionadoTcerNivel,
						fAux.getName(), valor);
				}

			    }
			    // Se valida que objetoRelacionadoTcerNivel NO se
			    // null para asignarcelo al objeto del segundo nivel
			    if (objetoRelacionadoTcerNivel != null) {
				Class<?>[] ListParametrosSdoNivel = new Class[1];
				ListParametrosSdoNivel[0] = clazzField;
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoTcerNivel
				Method metodoSetDtoSdoNivel = this
					.getMethodSet(field.getType(),
						relation.property(),
						ListParametrosSdoNivel);
				// si el metodo ex�ste se invoca modificando el
				// objeto de segundo nivel
				if (metodoSetDtoSdoNivel != null) {
				    metodoSetDtoSdoNivel.invoke(
					    objetoRelacionadoSdoNivel,
					    objetoRelacionadoTcerNivel);
				}
			    }
			    // Se valida que objetoRelacionadoSdoNivel NO se
			    // null para asignarcelo al objeto del primer nivel
			    if (objetoRelacionadoSdoNivel != null) {
				Class<?>[] ListParametros = new Class[1];
				ListParametros[0] = field.getType();
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoSdoNivel
				Method metodoSetDtoPmerNivel = this
					.getMethodSet(objetoRetorno.getClass(),
						field.getName(), ListParametros);
				// si el metodo ex�ste se invoca modificando el
				// objeto de primer nivel
				if (metodoSetDtoPmerNivel != null) {
				    metodoSetDtoPmerNivel.invoke(objetoRetorno,
					    objetoRelacionadoSdoNivel);
				}
			    }

			}
		    }
		} catch (SecurityException e) {

		    log.error(e.getMessage(), e);
		} catch (JDOMException e) {

		    log.error(e.getMessage(), e);
		} catch (IOException e) {

		    log.error(e.getMessage(), e);
		} catch (NoSuchFieldException e) {

		    log.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {

		    log.error(e.getMessage(), e);
		}
		// Se agrega el obejto de primer nivel a la lista de retorno.
		listaDatos.add((T) objetoRetorno);

	    }

	} catch (ClassNotFoundException e) {

	    log.error(e.getMessage(), e);
	} finally {
	    session.close();
	}

	return listaDatos;
    }

    /**
     * Busca en el ResultSet si existe una columna con el nombre que se envia
     * por parametro, si no la contiene en el bloque catch se retorna -1 en caso
     * contrario se retorna un entero >=0.
     * 
     * @param rs
     * @param nombreColumn
     * @return
     */
    public int findColumn(ResultSet rs, String nombreColumn) {
	try {
	    return rs.findColumn(nombreColumn);
	} catch (SQLException e) {
	    return -1;
	}
    }

    public int findColumn(Map<String, Object> mapa, String nombreColumn) {
	if (mapa.containsKey(nombreColumn))
	    return 1;
	else
	    return -1;

    }

    /**
     * Modifica el objetoDeClse dinamicamente de acuerdo al atributo que se
     * quiera modificar.
     * 
     * @param dto
     * @param cr
     * @param objetoDeClse
     * @param nombrAtributo
     * @param valor
     * @return
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ParseException
     */

    public Object getObjetoModificado(Class<?> dto, Column cr,
	    Object objetoDeClse, String nombrAtributo, Object valor)
	    throws ClassNotFoundException, SecurityException,
	    NoSuchMethodException, IllegalArgumentException,
	    IllegalAccessException, InvocationTargetException,
	    InstantiationException, ParseException {
	Class<?>[] ListParametros = new Class[1];
	ListParametros[0] = Class.forName(cr.type());
	// Se obtiene el metodo que modifica el atributo (nombrAtributo)
	Method metodoSet = this
		.getMethodSet(dto, nombrAtributo, ListParametros);
	Class<?> parser = Class.forName(cr.type());
	// si el metodo ex�ste se invoca modificando el objetoDeClse, si el
	// valor es diferente de null y ""
	if (metodoSet != null) {
	    if (valor != null && !valor.equals("")) {
		String dato = new String(valor.toString());
		// Se valida que el tipo no se DATE debido a que el constructor
		// de DATE que recibe un String esta deprecado
		if (!parser.isInstance(new Date())) {
		    // se otiene el constructor que recibe un String
		    Constructor<?> constructor = parser
			    .getDeclaredConstructor(String.class);
		    // si el Constructor es != null se se crea una nueva
		    // instancia asignandole el valor al metodo a invocar.
		    if (constructor != null) {
			constructor.setAccessible(true);
			metodoSet.invoke(objetoDeClse,
				constructor.newInstance(dato));
		    }
		} else {
		    // se modifica el valor del atributo haciendo un casting a
		    // Date.
		    metodoSet.invoke(objetoDeClse, (Date) valor);

		}
	    }
	}
	return objetoDeClse;

    }

    /**
     * Este M�todo busca en toda la clase un metodo por medio del nombreMetodo,y
     * los paramtros que recibe, si no lo encuentra en el bloque del cath se
     * as�gna null en la variable de retorno.
     * 
     * @param clase
     * @param nombreMetodo
     * @param parametos
     * @return
     */

    public Method getMethodSet(Class<?> clase, String nombreMetodo,
	    Class<?>[] parametos) {
	nombreMetodo = nombreMetodo.substring(0, 1).toUpperCase()
		+ nombreMetodo.substring(1);
	Method metodo;
	try {
	    metodo = clase.getMethod("set" + nombreMetodo, parametos);
	} catch (SecurityException e) {
	    metodo = null;
	} catch (NoSuchMethodException e) {
	    metodo = null;

	}
	return metodo;
    }

    @Override
    public Collection<Object> obtenerListado(String sqlName, int omitir,
	    int maximo) throws Exception {
	try {
	    return session.selectList(sqlName, new RowBounds(omitir, maximo));
	} finally {
	    session.close();
	}
    }

    @Override
    public Collection<Object> obtenerListado(String sqlName, Object objeto,
	    int omitir, int maximo) throws Exception {
	try {
	    return session.selectList(sqlName, objeto, new RowBounds(omitir,
		    maximo));
	} finally {
	    session.close();
	}
    }

    private String getSqlDinamico(String sql, String param1, String param2) {
	String sqlResult = "";
	if (param1 != null && param2 != null) {
	    sqlResult = sql.replaceFirst("?", " '" + param1 + "' ");
	    sqlResult = sqlResult.replace("?", " '" + param2 + "' ");
	} else if (param1 != null && param2 == null) {
	    sqlResult = sql.replace("?", " '" + param1 + "' ");
	}
	return sqlResult;
    }
    
    
    /**
     * Modifica los atributos dinamicamente del objetos enviado por par�metro
     * mediante el resultado de la consulta sql
     * 
     * @param <T>
     * @param tipoClase
     * @param sql
     * @param omitir
     * @param maximo
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ParseException
     * @throws Exception
     */
    public <T> List<T> listadoDinamico(Object tipoClase, String sql,
	    int omitir, int maximo)
	    throws SQLException, InterruptedException, NoSuchMethodException,
	    IllegalAccessException, InvocationTargetException,
	    InstantiationException, ParseException, Exception {
	// lista de retorno
	List<T> listaDatos = new LinkedList<T>();

	try {
	    // estas tres lineas crean la coneccion, preparan la sentencia y
	    // ejcutan la consulta.

	    Map<String, Object> parametros = new HashMap<String, Object>();

	    log.info("SQL Entrada =====> " + sql);

	    parametros.put("SQL_DINAMICA", sql);

	    Collection<Object> collection = obtenerListado("UTIL_sqlDinamico",
		    parametros,omitir,maximo);

	    // Se obtiene el tipor de calse del objeto enviado por par�metro
	    Class<?> clase = Class.forName(tipoClase.getClass().getName());
	    // Se obtiene el mapper configurado en la clase
	    Mapper maper = (Mapper) clase.getAnnotation(Mapper.class);
	    // Se obtiene el id del mapper configurado en la clase
	    String id = maper.id();

	    // Se recorre el resultado de la consulta
	    for (Object objeto : collection) {
		Map<String, Object> mapaRow = (Map<String, Object>) objeto;

		List<ColumnsMapper> columnsMappers = new LinkedList<ColumnsMapper>();
		// Se crea una nueva intancia del objeto recibido por
		// par�rametros.
		Object objetoRetorno = tipoClase.getClass().newInstance();
		try {
		    // Se obtienen los field declarados en la clase mapeada
		    for (Field field : DTOMapper.getInstance()
			    .getMapperReport(id).getClazz().getDeclaredFields()) {
			// columnas simples que corresponden a tipos de datos
			// primitivos como Long,String, Integer, etc.
			if (field.isAnnotationPresent(Column.class)) {
			    Column column = field.getAnnotation(Column.class);
			    columnsMappers.add(new ColumnsMapper(column.name(),
				    column.label(), column.type(), column
					    .property(), column.index()));
			    // Se valida que exista la columna en el ResultSet
			    if (findColumn(mapaRow, column.name()) >= 0) {
				// se obtiene el valor de la columna en el
				// ResultSet
				Object valor = mapaRow.get(column.name());
				// se modifica el atributo de la clase
				getObjetoModificado(objetoRetorno.getClass(),
					column, objetoRetorno,
					column.property(), valor);
			    }

			    // columnas relacionales Corresponden al segundo
			    // nivel.
			} else if (field.isAnnotationPresent(Relation.class)) {
			    Relation relation = field
				    .getAnnotation(Relation.class);
			    Class<?> clazzField = DTOMapper
				    .getInstance()
				    .getMapperReport(
					    relation.idMapperForRelation())
				    .getClazz();
			    Object objetoRelacionadoSdoNivel = null;
			    String[] filedsRelation = relation.fields();
			    int j = 0;
			    for (int i = 0; i < filedsRelation.length; i++) {
				Field fAux = clazzField
					.getDeclaredField(filedsRelation[i]);
				Column cr = fAux.getAnnotation(Column.class);
				// Se valida que exista la columna en el
				// ResultSet
				if (findColumn(mapaRow,
					relation.columnsName()[i]) >= 0) {
				    // se obtiene el valor de la columna en el
				    // ResultSet
				    Object valor = mapaRow.get(relation
					    .columnsName()[i]);
				    // se valida que el objeto por lo menes
				    // tenga un atributo a modificar.
				    if (valor != null && j == 0) {
					objetoRelacionadoSdoNivel = clazzField
						.newInstance();
					j++;
				    }
				    if (objetoRelacionadoSdoNivel != null)
					// se modifica el atributo de la clase
					getObjetoModificado(clazzField, cr,
						objetoRelacionadoSdoNivel,
						fAux.getName(), valor);
				}

			    }
			    // Se valida que objetoRelacionadoSdoNivel NO se
			    // null para asignarcelo al objeto del primer nivel
			    if (objetoRelacionadoSdoNivel != null) {
				Class<?>[] ListParametros = new Class[1];
				ListParametros[0] = clazzField;
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoSdoNivel
				Method metodoSetDto = this.getMethodSet(
					objetoRetorno.getClass(),
					field.getName(), ListParametros);
				// si el metodo ex�ste se invoca modificando el
				// objeto de primer nivel.
				if (metodoSetDto != null) {
				    metodoSetDto.invoke(objetoRetorno,
					    objetoRelacionadoSdoNivel);
				}
			    }
			    // columnas relacionales Corresponden al tercer
			    // nivel.
			} else if (field
				.isAnnotationPresent(RelationFilter.class)) {
			    RelationFilter relation = field
				    .getAnnotation(RelationFilter.class);
			    Class<?> clazzField = DTOMapper
				    .getInstance()
				    .getMapperReport(
					    relation.idMapperForRelation())
				    .getClazz();
			    String[] filedsRelation = relation.fields();
			    Object objetoRelacionadoSdoNivel = field.getType()
				    .newInstance();
			    Object objetoRelacionadoTcerNivel = null;
			    int j = 0;
			    for (int i = 0; i < filedsRelation.length; i++) {
				Field fAux = clazzField
					.getDeclaredField(filedsRelation[i]);
				Column cr = fAux.getAnnotation(Column.class);
				if (findColumn(mapaRow,
					relation.columnsName()[i]) >= 0) {
				    // se obtiene el valor de la columna en el
				    // ResultSet
				    Object valor = mapaRow.get(relation
					    .columnsName()[i]);
				    // se valida que el objeto por lo menes
				    // tenga un atributo a modificar.
				    if (valor != null && j == 0) {
					objetoRelacionadoTcerNivel = clazzField
						.newInstance();
					j++;
				    }
				    if (objetoRelacionadoTcerNivel != null)
					// se modifica el atributo de la clase
					getObjetoModificado(clazzField, cr,
						objetoRelacionadoTcerNivel,
						fAux.getName(), valor);
				}

			    }
			    // Se valida que objetoRelacionadoTcerNivel NO se
			    // null para asignarcelo al objeto del segundo nivel
			    if (objetoRelacionadoTcerNivel != null) {
				Class<?>[] ListParametrosSdoNivel = new Class[1];
				ListParametrosSdoNivel[0] = clazzField;
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoTcerNivel
				Method metodoSetDtoSdoNivel = this
					.getMethodSet(field.getType(),
						relation.property(),
						ListParametrosSdoNivel);
				// si el metodo ex�ste se invoca modificando el
				// objeto de segundo nivel
				if (metodoSetDtoSdoNivel != null) {
				    metodoSetDtoSdoNivel.invoke(
					    objetoRelacionadoSdoNivel,
					    objetoRelacionadoTcerNivel);
				}
			    }
			    // Se valida que objetoRelacionadoSdoNivel NO se
			    // null para asignarcelo al objeto del primer nivel
			    if (objetoRelacionadoSdoNivel != null) {
				Class<?>[] ListParametros = new Class[1];
				ListParametros[0] = field.getType();
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoSdoNivel
				Method metodoSetDtoPmerNivel = this
					.getMethodSet(objetoRetorno.getClass(),
						field.getName(), ListParametros);
				// si el metodo ex�ste se invoca modificando el
				// objeto de primer nivel
				if (metodoSetDtoPmerNivel != null) {
				    metodoSetDtoPmerNivel.invoke(objetoRetorno,
					    objetoRelacionadoSdoNivel);
				}
			    }

			}
		    }
		} catch (SecurityException e) {

		    log.error(e.getMessage(), e);
		} catch (JDOMException e) {

		    log.error(e.getMessage(), e);
		} catch (IOException e) {

		    log.error(e.getMessage(), e);
		} catch (NoSuchFieldException e) {

		    log.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {

		    log.error(e.getMessage(), e);
		}

		listaDatos.add((T) objetoRetorno);

	    }

	} catch (ClassNotFoundException e) {

	    log.error(e.getMessage(), e);
	} finally {
	    session.close();
	}

	return listaDatos;
    }
    
    
    /**
     * 
     * @param <T>
     * @param tipoClase
     * @param sql
     * @param parametros
     * @param omitir
     * @param maximo
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ParseException
     * @throws Exception
     */
    public <T> List<T> listadoDinamico(Object tipoClase, String sql,
	    Map<String, Object> parametros,int omitir, int maximo) throws SQLException,
	    InterruptedException, NoSuchMethodException,
	    IllegalAccessException, InvocationTargetException,
	    InstantiationException, ParseException, Exception {
	// lista de retorno
	List<T> listaDatos = new LinkedList<T>();

	try {
	    // estas tres lineas crean la coneccion, preparan la sentencia y
	    // ejcutan la consulta.
	    String sqlResult = sql;
	    IBeanAbstracto beanAbstracto = (IBeanAbstracto) parametros
		    .get("OBJETO");

	    if (beanAbstracto.getCodigo() != null
		    && beanAbstracto.getNombre() != null) {
		sqlResult = getSqlDinamico(sql, beanAbstracto.getCodigo(),
			beanAbstracto.getNombre());
	    } else if (beanAbstracto.getCodigo() != null) {
		sqlResult = getSqlDinamico(sql, beanAbstracto.getCodigo(), null);
	    } else if (beanAbstracto.getNombre() != null) {
		sqlResult = getSqlDinamico(sql, beanAbstracto.getNombre(), null);
	    }

	    parametros.put("SQL_DINAMICA", sqlResult);

	    Collection<Object> collection = obtenerListado("UTIL_sqlDinamico",
		    parametros,omitir,maximo);

	    // Se obtiene el tipor de clase del objeto enviado por par�metro
	    Class<?> clase = Class.forName(tipoClase.getClass().getName());
	    // Se obtiene el mapper configurado en la clase
	    Mapper maper = (Mapper) clase.getAnnotation(Mapper.class);
	    // Se obtiene el id del mapper configurado en la clase
	    String id = maper.id();

	    // Se recorre el resultado de la consulta
	    for (Object objeto : collection) {
		Map<String, Object> mapaRow = (Map<String, Object>) objeto;

		List<ColumnsMapper> columnsMappers = new LinkedList<ColumnsMapper>();
		// Se crea una nueva intancia del objeto recibido por
		// par�rametros.
		Object objetoRetorno = tipoClase.getClass().newInstance();
		try {
		    // Se obtienen los field declarados en la clase mapeada
		    for (Field field : DTOMapper.getInstance()
			    .getMapperReport(id).getClazz().getDeclaredFields()) {
			// columnas simples que corresponden a tipos de datos
			// primitivos como Long,String, Integer, etc.
			if (field.isAnnotationPresent(Column.class)) {
			    Column column = field.getAnnotation(Column.class);
			    columnsMappers.add(new ColumnsMapper(column.name(),
				    column.label(), column.type(), column
					    .property(), column.index()));
			    // Se valida que exista la columna en el ResultSet
			    if (findColumn(mapaRow, column.name()) >= 0) {
				// se obtiene el valor de la columna en el
				// ResultSet
				Object valor = mapaRow.get(column.name());
				// se modifica el atributo de la clase
				getObjetoModificado(objetoRetorno.getClass(),
					column, objetoRetorno,
					column.property(), valor);
			    }

			    // columnas relacionales Corresponden al segundo
			    // nivel.
			} else if (field.isAnnotationPresent(Relation.class)) {
			    Relation relation = field
				    .getAnnotation(Relation.class);
			    Class<?> clazzField = DTOMapper
				    .getInstance()
				    .getMapperReport(
					    relation.idMapperForRelation())
				    .getClazz();
			    Object objetoRelacionadoSdoNivel = null;
			    String[] filedsRelation = relation.fields();
			    int j = 0;
			    for (int i = 0; i < filedsRelation.length; i++) {
				Field fAux = clazzField
					.getDeclaredField(filedsRelation[i]);
				Column cr = fAux.getAnnotation(Column.class);
				// Se valida que exista la columna en el
				// ResultSet
				if (findColumn(mapaRow,
					relation.columnsName()[i]) >= 0) {
				    // se obtiene el valor de la columna en el
				    // ResultSet
				    Object valor = mapaRow.get(relation
					    .columnsName()[i]);
				    // se valida que el objeto por lo menes
				    // tenga un atributo a modificar.
				    if (valor != null && j == 0) {
					objetoRelacionadoSdoNivel = clazzField
						.newInstance();
					j++;
				    }
				    if (objetoRelacionadoSdoNivel != null)
					// se modifica el atributo de la clase
					getObjetoModificado(clazzField, cr,
						objetoRelacionadoSdoNivel,
						fAux.getName(), valor);
				}

			    }
			    // Se valida que objetoRelacionadoSdoNivel NO se
			    // null para asignarcelo al objeto del primer nivel
			    if (objetoRelacionadoSdoNivel != null) {
				Class<?>[] ListParametros = new Class[1];
				ListParametros[0] = clazzField;
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoSdoNivel
				Method metodoSetDto = this.getMethodSet(
					objetoRetorno.getClass(),
					field.getName(), ListParametros);
				// si el metodo ex�ste se invoca modificando el
				// objeto de primer nivel.
				if (metodoSetDto != null) {
				    metodoSetDto.invoke(objetoRetorno,
					    objetoRelacionadoSdoNivel);
				}
			    }
			    // columnas relacionales Corresponden al tercer
			    // nivel.
			} else if (field
				.isAnnotationPresent(RelationFilter.class)) {
			    RelationFilter relation = field
				    .getAnnotation(RelationFilter.class);
			    Class<?> clazzField = DTOMapper
				    .getInstance()
				    .getMapperReport(
					    relation.idMapperForRelation())
				    .getClazz();
			    String[] filedsRelation = relation.fields();
			    Object objetoRelacionadoSdoNivel = field.getType()
				    .newInstance();
			    Object objetoRelacionadoTcerNivel = null;
			    int j = 0;
			    for (int i = 0; i < filedsRelation.length; i++) {
				Field fAux = clazzField
					.getDeclaredField(filedsRelation[i]);
				Column cr = fAux.getAnnotation(Column.class);
				if (findColumn(mapaRow,
					relation.columnsName()[i]) >= 0) {
				    // se obtiene el valor de la columna en el
				    // ResultSet
				    Object valor = mapaRow.get(relation
					    .columnsName()[i]);
				    // se valida que el objeto por lo menes
				    // tenga un atributo a modificar.
				    if (valor != null && j == 0) {
					objetoRelacionadoTcerNivel = clazzField
						.newInstance();
					j++;
				    }
				    if (objetoRelacionadoTcerNivel != null)
					// se modifica el atributo de la clase
					getObjetoModificado(clazzField, cr,
						objetoRelacionadoTcerNivel,
						fAux.getName(), valor);
				}

			    }
			    // Se valida que objetoRelacionadoTcerNivel NO se
			    // null para asignarcelo al objeto del segundo nivel
			    if (objetoRelacionadoTcerNivel != null) {
				Class<?>[] ListParametrosSdoNivel = new Class[1];
				ListParametrosSdoNivel[0] = clazzField;
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoTcerNivel
				Method metodoSetDtoSdoNivel = this
					.getMethodSet(field.getType(),
						relation.property(),
						ListParametrosSdoNivel);
				// si el metodo ex�ste se invoca modificando el
				// objeto de segundo nivel
				if (metodoSetDtoSdoNivel != null) {
				    metodoSetDtoSdoNivel.invoke(
					    objetoRelacionadoSdoNivel,
					    objetoRelacionadoTcerNivel);
				}
			    }
			    // Se valida que objetoRelacionadoSdoNivel NO se
			    // null para asignarcelo al objeto del primer nivel
			    if (objetoRelacionadoSdoNivel != null) {
				Class<?>[] ListParametros = new Class[1];
				ListParametros[0] = field.getType();
				// Se obtiene el metodo que modifica el
				// objetoRelacionadoSdoNivel
				Method metodoSetDtoPmerNivel = this
					.getMethodSet(objetoRetorno.getClass(),
						field.getName(), ListParametros);
				// si el metodo ex�ste se invoca modificando el
				// objeto de primer nivel
				if (metodoSetDtoPmerNivel != null) {
				    metodoSetDtoPmerNivel.invoke(objetoRetorno,
					    objetoRelacionadoSdoNivel);
				}
			    }

			}
		    }
		} catch (SecurityException e) {

		    log.error(e.getMessage(), e);
		} catch (JDOMException e) {

		    log.error(e.getMessage(), e);
		} catch (IOException e) {

		    log.error(e.getMessage(), e);
		} catch (NoSuchFieldException e) {

		    log.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {

		    log.error(e.getMessage(), e);
		}
		// Se agrega el obejto de primer nivel a la lista de retorno.
		listaDatos.add((T) objetoRetorno);

	    }

	} catch (ClassNotFoundException e) {

	    log.error(e.getMessage(), e);
	} finally {
	    session.close();
	}

	return listaDatos;
    }
    
    
    /**
     * 
     * @param sql
     * @return
     * @throws SQLException
     * @throws InterruptedException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ParseException
     * @throws Exception
     */
    public Object obtenerDinamico(String sql, Map<String,Object> parametros)
	    throws SQLException, InterruptedException, NoSuchMethodException,
	    IllegalAccessException, InvocationTargetException,
	    InstantiationException, ParseException, Exception {

	parametros.put("SQL_DINAMICA", sql);
	Object clase = obtenerRegistro("UTIL_sqlDinamico", parametros);
	return clase;
    }
    
    /**
     * Metodo para obtener una consulta sql de un mapper(XML)
     * @param sqlName
     * @param objeto
     * @return
     * @throws Exception
     */
    public String obtenerSqlMapper(String sqlName, Object objeto)throws Exception{
	 String sqlMapper = session.getConfiguration().getMappedStatement(sqlName).getBoundSql(objeto).getSql();
	 
	 sqlMapper = sqlMapper.replaceAll("[\n\r]", " ");
	
	 List<ParameterMapping> listaParametros = session.getConfiguration().getMappedStatement(sqlName).getBoundSql(objeto).getParameterMappings();
	 
	 for(ParameterMapping parameterMapping : listaParametros){
	     StringBuffer remplazoSqlBuffer = new StringBuffer("#{").append(parameterMapping.getProperty());
	     if(parameterMapping.getJdbcType() != null && !parameterMapping.getJdbcType().toString().isEmpty()){
		 remplazoSqlBuffer.append(", jdbcType=").append(parameterMapping.getJdbcType().toString());
	     }
	     if(parameterMapping.getMode() != null && !parameterMapping.getMode().toString().isEmpty()){
		 remplazoSqlBuffer.append(", mode=").append(parameterMapping.getMode().toString());
	     }
	     if(parameterMapping.getJavaType() != null){
		 remplazoSqlBuffer.append(", javaType=").append(parameterMapping.getJavaType().getName());
	     }
	     remplazoSqlBuffer.append("}");
	     
	     sqlMapper = sqlMapper.replaceFirst("[?]", remplazoSqlBuffer.toString());
	 }
	 return sqlMapper;
    }
    

}
