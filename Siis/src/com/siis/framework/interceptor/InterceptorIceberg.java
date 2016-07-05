package com.casewaresa.framework.interceptor;

import java.sql.CallableStatement;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;

import com.casewaresa.iceberg_ln.dto.LNTLiquidacion;

/**
 * 
 * {@link Interceptor} que captura el llamado de los {@link MappedStatement} que
 * modifican el estado de los registros en la base de datos [delete, update,
 * insert]
 * 
 * @author jmora
 * 
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = {
	MappedStatement.class, Object.class }) })
public class InterceptorIceberg implements Interceptor {

    protected static Logger log = Logger.getLogger(InterceptorIceberg.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin
     * .Invocation)
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
	log.debug("Ejecutando Metodo [ invocation ]");

	Map<?, ?> auditoria = null;
	if (((MappedStatement) invocation.getArgs()[0]).getId().equals(
		"LNLiquidadoMapper.ICE_LN_LIQUIDAR")) {
	    auditoria = ((LNTLiquidacion) invocation.getArgs()[1])
		    .getAuditoria();
	} else {
	    auditoria = (Map<?, ?>) Executions.getCurrent().getSession()
		    .getAttribute("AUDITORIA");
	}

	log.trace(auditoria);
	if (auditoria != null && !auditoria.isEmpty()
		&& auditoria.get("CONS_SEC_CUENTA_ICEBERG") != null) {
	  
	    String levantarContexto = "{call BEGIN GEP_Contexto.set_Valor( GEP_Contexto.CONS_CONTEXTO_LOCAL, GEP_Contexto.CONS_SEC_CUENTA_ICEBERG, ? ); GEP_Contexto.set_Valor( GEP_Contexto.CONS_CONTEXTO_LOCAL, GEP_Contexto.CONS_SEC_EJECUTABLE, ?); GEP_Contexto.set_VALOR( GEP_Contexto.CONS_CONTEXTO_LOCAL, GEP_Contexto.CONS_AUDITORIA_NUEVO, ? ); GEP_Contexto.set_VALOR( GEP_Contexto.CONS_CONTEXTO_LOCAL, GEP_Contexto.CONS_AUDITORIA_ACTUALIZADO, ? ); GEP_Contexto.set_VALOR( GEP_Contexto.CONS_CONTEXTO_LOCAL, GEP_Contexto.CONS_AUDITORIA_ELIMINADO, ? ); EXCEPTION WHEN OTHERS THEN GEP_CONTEXTO.Limpiar_Contexto(GEP_Contexto.CONS_CONTEXTO_LOCAL); ICE_Excepcion.Error; END }";

	    CallableStatement callableStatement = ((CachingExecutor) invocation
		    .getTarget()).getTransaction().getConnection()
		    .prepareCall(levantarContexto);

	    callableStatement.setObject(1,
		    auditoria.get("CONS_SEC_CUENTA_ICEBERG").toString());
	    callableStatement.setObject(2, auditoria.get("CONS_SEC_EJECUTABLE")
		    .toString());
	    callableStatement.setObject(3, auditoria
		    .get("CONS_AUDITORIA_NUEVO").toString());
	    callableStatement.setObject(4,
		    auditoria.get("CONS_AUDITORIA_ACTUALIZADO").toString());
	    callableStatement.setObject(5,
		    auditoria.get("CONS_AUDITORIA_ELIMINADO").toString());
	    
	    log.trace("Levantando contexto de auditoria");
	    
	    callableStatement.execute();
	    
	    log.trace("Contexto de auditoria [OK]");
	}

	Object object = null;
	try {
	    object = invocation.proceed();
	} catch (Exception e) {
	    log.error(e);
	    throw e;
	} finally {
	    if (auditoria != null && !auditoria.isEmpty()
		    && auditoria.get("CONS_SEC_CUENTA_ICEBERG") != null) {
		String levantarContexto = "{call BEGIN GEP_CONTEXTO.Limpiar_Contexto(GEP_Contexto.CONS_CONTEXTO_LOCAL); END }";

		CallableStatement callableStatement = ((CachingExecutor) invocation
			.getTarget()).getTransaction().getConnection()
			.prepareCall(levantarContexto);
		log.trace("####Ejecutando limpiar contexto");
		callableStatement.execute();
		log.trace("####Fin limpiar contexto");
	    }

	}

	return object;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
     */
    @Override
    public Object plugin(Object object) {
	return Plugin.wrap(object, this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
     */
    @Override
    public void setProperties(Properties arg0) {

    }

}
