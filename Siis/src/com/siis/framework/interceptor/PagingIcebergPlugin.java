package com.casewaresa.framework.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.ssmm2.common.persistence.dialect.Dialect;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
	MappedStatement.class, Object.class, RowBounds.class,
	ResultHandler.class }) })
public class PagingIcebergPlugin implements Interceptor {
    protected static Logger log = Logger.getLogger(PagingIcebergPlugin.class);
    private String dialectClass;

    public void setDialectClass(String dialectClass) {
	this.dialectClass = dialectClass;
    }

    final static int MAPPED_STATEMENT_INDEX = 0;
    final static int PARAMETER_INDEX = 1;
    final static int ROWBOUNDS_INDEX = 2;
    final static int RESULT_HANDLER_INDEX = 3;

    Dialect dialect;

    public Object intercept(Invocation invocation) throws Throwable {
	processIntercept(invocation.getArgs());
	return invocation.proceed();
    }

    void processIntercept(final Object[] queryArgs) {
	if (dialectClass != null) {
	    this.setDialect(dialectClass);
	}
	MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
	Object parameter = queryArgs[PARAMETER_INDEX];
	final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
	int offset = rowBounds.getOffset();
	int limit = rowBounds.getLimit();

	if (dialect.supportsLimit()
		&& (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
	    BoundSql boundSql = ms.getBoundSql(parameter);
	    String sql = boundSql.getSql().trim();
	    if (dialect.supportsLimitOffset()) {
		sql = dialect.getLimitString(sql, offset, limit);
		offset = RowBounds.NO_ROW_OFFSET;
	    } else {
		sql = dialect.getLimitString(sql, 0, limit);
	    }
	    limit = RowBounds.NO_ROW_LIMIT;

	    queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
	    BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql,
		    boundSql.getParameterMappings(),
		    boundSql.getParameterObject());
	    MappedStatement newMs = copyFromMappedStatement(ms,
		    new BoundSqlSqlSource(newBoundSql));
	    queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
	}
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms,
	    SqlSource newSqlSource) {

	MappedStatement.Builder builder = new MappedStatement.Builder(
		ms.getConfiguration(), ms.getId(), newSqlSource,
		ms.getSqlCommandType());

	builder.resource(ms.getResource());
	builder.fetchSize(ms.getFetchSize());
	builder.statementType(ms.getStatementType());
	builder.keyGenerator(ms.getKeyGenerator());
	
	if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
	    builder.keyProperty(ms.getKeyProperties()[0]);
	}

	builder.timeout(ms.getTimeout());
	builder.parameterMap(ms.getParameterMap());
	builder.resultMaps(ms.getResultMaps());
	builder.cache(ms.getCache());
	MappedStatement newMs = builder.build();

	return newMs;
    }

    public Object plugin(Object target) {
	return Plugin.wrap(target, this);
    }

    public static class BoundSqlSqlSource implements SqlSource {
	BoundSql boundSql;

	public BoundSqlSqlSource(BoundSql boundSql) {
	    this.boundSql = boundSql;
	}

	public BoundSql getBoundSql(Object parameterObject) {
	    return boundSql;
	}
    }

    public void setProperties(Properties properties) {
	if (dialectClass == null) {
	    dialectClass = properties.getProperty("dialectClass");
	}
	this.setDialect(dialectClass);

    }

    private void setDialect(String dialectClass) {
	try {
	    dialect = (Dialect) Class.forName(dialectClass).newInstance();
	} catch (Exception e) {
	    throw new RuntimeException(
		    "cannot create dialect instance by dialectClass:"
			    + dialectClass, e);
	}	
    }
}
