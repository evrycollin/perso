package org.jboss.as.quickstart.hibernate4.vertrica;

/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 */

import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.NvlFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;

import org.hibernate.internal.util.JdbcExceptionHelper;
import org.hibernate.type.StandardBasicTypes;

public class VerticaDialect6 extends Dialect {

    public VerticaDialect6() {
	super();
	registerCharacterTypeMappings();
	registerNumericTypeMappings();
	registerDateTimeTypeMappings();
	registerLargeObjectTypeMappings();

	registerOtherTypeMappings();

	registerReverseHibernateTypeMappings();

	registerFunctions();

	registerDefaultProperties();
    }

    protected void registerCharacterTypeMappings() {
	registerColumnType(Types.CHAR, "char(1)");
	registerColumnType(Types.VARCHAR, "varchar($l)");
    }

    protected void registerNumericTypeMappings() {
	registerColumnType(Types.BIT, "bool");
	registerColumnType(Types.BIGINT, "int");
	registerColumnType(Types.SMALLINT, "int");
	registerColumnType(Types.TINYINT, "int");
	registerColumnType(Types.INTEGER, "int");

	registerColumnType(Types.FLOAT, "float4");
	registerColumnType(Types.DOUBLE, "float8");
	registerColumnType(Types.NUMERIC, "numeric($p,$s)");
	registerColumnType(Types.DECIMAL, "numeric($p,$s)");
    }

    protected void registerDateTimeTypeMappings() {
	registerColumnType(Types.DATE, "date");
	registerColumnType(Types.TIME, "time");
	registerColumnType(Types.TIMESTAMP, "timestamp");
    }

    protected void registerLargeObjectTypeMappings() {
	registerColumnType(Types.VARBINARY, 65000, "varbinary($l)");
	registerColumnType(Types.BINARY, 65000, "varbinary($l)");

	registerColumnType(Types.LONGVARCHAR, 32000000, "varchar($l)");
	registerColumnType(Types.LONGVARBINARY, 32000000, "varbinary($l)");
    }

    protected void registerOtherTypeMappings() {
	registerColumnType(Types.OTHER, "varchar($l)");
    }

    protected void registerReverseHibernateTypeMappings() {
    }

    protected void registerFunctions() {
	registerFunction("abs", new StandardSQLFunction("abs"));
	registerFunction("sign", new StandardSQLFunction("sign",
		StandardBasicTypes.INTEGER));

	registerFunction("acos", new StandardSQLFunction("acos",
		StandardBasicTypes.DOUBLE));
	registerFunction("asin", new StandardSQLFunction("asin",
		StandardBasicTypes.DOUBLE));
	registerFunction("atan", new StandardSQLFunction("atan",
		StandardBasicTypes.DOUBLE));
	registerFunction("cos", new StandardSQLFunction("cos",
		StandardBasicTypes.DOUBLE));
	registerFunction("exp", new StandardSQLFunction("exp",
		StandardBasicTypes.DOUBLE));
	registerFunction("ln", new StandardSQLFunction("ln",
		StandardBasicTypes.DOUBLE));
	registerFunction("sin", new StandardSQLFunction("sin",
		StandardBasicTypes.DOUBLE));
	registerFunction("stddev", new StandardSQLFunction("stddev",
		StandardBasicTypes.DOUBLE));
	registerFunction("sqrt", new StandardSQLFunction("sqrt",
		StandardBasicTypes.DOUBLE));
	registerFunction("tan", new StandardSQLFunction("tan",
		StandardBasicTypes.DOUBLE));
	registerFunction("variance", new StandardSQLFunction("variance",
		StandardBasicTypes.DOUBLE));

	registerFunction("round", new StandardSQLFunction("round"));
	registerFunction("trunc", new StandardSQLFunction("trunc"));
	registerFunction("ceil", new StandardSQLFunction("ceil"));
	registerFunction("floor", new StandardSQLFunction("floor"));

	registerFunction("chr", new StandardSQLFunction("chr",
		StandardBasicTypes.CHARACTER));
	registerFunction("initcap", new StandardSQLFunction("initcap"));
	registerFunction("lower", new StandardSQLFunction("lower"));
	registerFunction("ltrim", new StandardSQLFunction("ltrim"));
	registerFunction("rtrim", new StandardSQLFunction("rtrim"));
	registerFunction("upper", new StandardSQLFunction("upper"));
	registerFunction("ascii", new StandardSQLFunction("ascii",
		StandardBasicTypes.INTEGER));

	registerFunction("to_char", new StandardSQLFunction("to_char",
		StandardBasicTypes.STRING));
	registerFunction("to_date", new StandardSQLFunction("to_date",
		StandardBasicTypes.TIMESTAMP));

	registerFunction("current_date", new NoArgSQLFunction("current_date",
		StandardBasicTypes.DATE, false));
	registerFunction("current_time", new NoArgSQLFunction(
		"current_timestamp", StandardBasicTypes.TIME, false));
	registerFunction("current_timestamp", new NoArgSQLFunction(
		"current_timestamp", StandardBasicTypes.TIMESTAMP, false));

	registerFunction("last_day", new StandardSQLFunction("last_day",
		StandardBasicTypes.DATE));
	registerFunction("sysdate", new NoArgSQLFunction("sysdate",
		StandardBasicTypes.DATE, false));
	registerFunction("user", new NoArgSQLFunction("user",
		StandardBasicTypes.STRING, false));

	// Multi-param string dialect functions...
	registerFunction("concat", new VarArgsSQLFunction(
		StandardBasicTypes.STRING, "", "||", ""));
	registerFunction("instr", new StandardSQLFunction("instr",
		StandardBasicTypes.INTEGER));
	registerFunction("instrb", new StandardSQLFunction("instrb",
		StandardBasicTypes.INTEGER));
	registerFunction("lpad", new StandardSQLFunction("lpad",
		StandardBasicTypes.STRING));
	registerFunction("replace", new StandardSQLFunction("replace",
		StandardBasicTypes.STRING));
	registerFunction("rpad", new StandardSQLFunction("rpad",
		StandardBasicTypes.STRING));
	registerFunction("substr", new StandardSQLFunction("substr",
		StandardBasicTypes.STRING));
	registerFunction("substrb", new StandardSQLFunction("substrb",
		StandardBasicTypes.STRING));
	registerFunction("translate", new StandardSQLFunction("translate",
		StandardBasicTypes.STRING));

	registerFunction("substring", new StandardSQLFunction("substr",
		StandardBasicTypes.STRING));
	registerFunction("bit_length", new SQLFunctionTemplate(
		StandardBasicTypes.INTEGER, "vsize(?1)*8"));
	registerFunction("coalesce", new NvlFunction());

	// Multi-param numeric dialect functions...
	registerFunction("atan2", new StandardSQLFunction("atan2",
		StandardBasicTypes.FLOAT));
	registerFunction("log", new StandardSQLFunction("log",
		StandardBasicTypes.INTEGER));
	registerFunction("mod", new StandardSQLFunction("mod",
		StandardBasicTypes.INTEGER));
	registerFunction("nvl", new StandardSQLFunction("nvl"));
	registerFunction("nvl2", new StandardSQLFunction("nvl2"));
	registerFunction("power", new StandardSQLFunction("power",
		StandardBasicTypes.FLOAT));

	// Multi-param date dialect functions...
	registerFunction("add_months", new StandardSQLFunction("add_months",
		StandardBasicTypes.DATE));
	registerFunction("months_between", new StandardSQLFunction(
		"months_between", StandardBasicTypes.FLOAT));
	registerFunction("next_day", new StandardSQLFunction("next_day",
		StandardBasicTypes.DATE));

    }

    protected void registerDefaultProperties() {
	getDefaultProperties().setProperty(Environment.USE_STREAMS_FOR_BINARY,
		"true");
	getDefaultProperties().setProperty(Environment.STATEMENT_BATCH_SIZE,
		DEFAULT_BATCH_SIZE);
    }

    /**
     * Allows access to the basic {@link Dialect#getSelectClauseNullString}
     * implementation...
     *
     * @param sqlType
     *            The {@link java.sql.Types} mapping type code
     * @return The appropriate select cluse fragment
     */
    public String getBasicSelectClauseNullString(int sqlType) {
	return super.getSelectClauseNullString(sqlType);
    }

    public String getSelectClauseNullString(int sqlType) {
	switch (sqlType) {
	case Types.VARCHAR:
	case Types.CHAR:
	    return "to_char(null)";
	case Types.DATE:
	case Types.TIMESTAMP:
	case Types.TIME:
	    return "to_date(null)";
	default:
	    return "to_number(null)";
	}
    }

    @Override
    public boolean bindLimitParametersInReverseOrder() {
	return true;
    }

    @Override
    public boolean dropConstraints() {
	return false;
    }

    @Override
    public String getAddColumnString() {
	return "add column";
    }

    @Override
    public String getCascadeConstraintsString() {
	return " cascade";
    }

    @Override
    public String getCreateSequenceString(String sequenceName) {
	return "create sequence " + sequenceName; // starts with 1, implicitly
    }

    @Override
    public String getCreateTemporaryTablePostfix() {
	return "on commit delete rows";
    }

    @Override
    public String getCreateTemporaryTableString() {
	return "create temporary table";
    }

    @Override
    public String getCurrentTimestampSelectString() {
	return "select now()";
    }

    @Override
    public String getDropSequenceString(String sequenceName) {
	return "drop sequence " + sequenceName;
    }

    @Override
    public String getForUpdateString(String aliases) {
	return getForUpdateString() + " of " + aliases;
    }

    @Override
    public String getIdentityColumnString(int type) {
	return "not null auto_increment"; // starts with 1, implicitly
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) {
	return "select last_insert_id()";
    }

    @Override
    public String getLimitString(String sql, boolean hasOffset) {
	return new StringBuilder(sql.length() + 20).append(sql)
		.append(hasOffset ? " limit ? offset ?" : " limit ?")
		.toString();
    }

    @Override
    public Class<? extends IdentifierGenerator> getNativeIdentifierGeneratorClass() {
	return SequenceGenerator.class;
    }

    @Override
    public String getNoColumnsInsertString() {
	return "default values";
    }

    @Override
    public String getQuerySequencesString() {
	return "select sequence_name from sequences";
    }

    @Override
    public String getSelectSequenceNextValString(String sequenceName) {
	return "nextval('" + sequenceName + "')";
    }

    @Override
    public String getSequenceNextValString(String sequenceName) {
	return "select " + getSelectSequenceNextValString(sequenceName);
    }

    @Override
    public boolean hasDataTypeInIdentityColumn() {
	return false;
    }

    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
	return false;
    }

    @Override
    public boolean supportsCommentOn() {
	return true;
    }

    @Override
    public boolean supportsCurrentTimestampSelection() {
	return true;
    }

    @Override
    public boolean supportsEmptyInList() {
	return false;
    }

    @Override
    public boolean supportsIdentityColumns() {
	return true;
    }

    @Override
    public boolean supportsLimit() {
	return true;
    }

    @Override
    public boolean supportsLobValueChangePropogation() {
	return false;
    }

    @Override
    public boolean supportsPooledSequences() {
	return true;
    }

    // Overridden informational metadata ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public boolean supportsSequences() {
	return true;
    }

    @Override
    public boolean supportsTemporaryTables() {
	return true;
    }

    @Override
    public boolean supportsTupleDistinctCounts() {
	return false;
    }

    @Override
    public boolean supportsUnboundedLobLocatorMaterialization() {
	return false;
    }

    @Override
    public boolean supportsUnionAll() {
	return true;
    }

    @Override
    public String toBooleanValueString(boolean bool) {
	return bool ? "true" : "false";
    }

    @Override
    public boolean useInputStreamToInsertBlob() {
	return false;
    }

    public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
	return EXTRACTER;
    }

    private static ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter() {

	/**
	 * Extract the name of the violated constraint from the given
	 * SQLException.
	 *
	 * @param sqle
	 *            The exception that was the result of the constraint
	 *            violation.
	 * @return The extracted constraint name.
	 */
	public String extractConstraintName(SQLException sqle) {
	    int errorCode = JdbcExceptionHelper.extractErrorCode(sqle);
	    if (errorCode == 1 || errorCode == 2291 || errorCode == 2292) {
		return extractUsingTemplate("constraint (", ") violated",
			sqle.getMessage());
	    } else if (errorCode == 1400) {
		// simple nullability constraint
		return null;
	    } else {
		return null;
	    }
	}

    };

}