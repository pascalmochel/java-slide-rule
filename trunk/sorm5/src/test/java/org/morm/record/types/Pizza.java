package org.morm.record.types;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

import org.morm.record.Entity;
import org.morm.record.field.impl.FBigDecimal;
import org.morm.record.field.impl.date.FDate;
import org.morm.record.field.impl.date.FTime;
import org.morm.record.field.impl.date.FTimestamp;
import org.morm.record.field.impl.primitive.FBoolean;
import org.morm.record.field.impl.primitive.FDouble;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.field.impl.primitive.FShort;
import org.morm.record.identity.IdentityGenerator;
import org.morm.record.identity.impl.hsqldb.HsqldbIdentity;

import java.sql.Date;

public class Pizza extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_PIZZA"));

	public static FShort tshort = new FShort("T_SHORT");
	public static FDouble tdouble = new FDouble("T_DOUBLE");
	public static FBoolean tbool = new FBoolean("T_BOOL");
	public static FBigDecimal tbigdec = new FBigDecimal("T_BIGDEC");

	public static FDate tdate = new FDate("T_DATE");
	public static FTime ttime = new FTime("T_TIME");
	public static FTimestamp ttimestamp = new FTimestamp("T_TIMESTAMP");

	public Pizza() {
		setTableName("PIZZA");
		registerIdField(id);
		registerFields(tshort, tdouble, tbool, tbigdec);
		registerFields(tdate, ttime, ttimestamp);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(Pizza.id, id);
	}

	public Short getTShort() {
		return get(tshort);
	}

	public void setTShort(final Short tshort) {
		set(Pizza.tshort, tshort);
	}

	public Double getTDouble() {
		return get(tdouble);
	}

	public void setTDouble(final Double tshort) {
		set(Pizza.tdouble, tshort);
	}

	public Boolean getTBoolean() {
		return get(tbool);
	}

	public void setTBoolean(final Boolean tshort) {
		set(Pizza.tbool, tshort);
	}

	public BigDecimal getTBigDec() {
		return get(tbigdec);
	}

	public void setTBigDec(final BigDecimal tshort) {
		set(Pizza.tbigdec, tshort);
	}

	public Date getTDate() {
		return get(tdate);
	}

	public void setTDate(final Date tshort) {
		set(Pizza.tdate, tshort);
	}

	public Time getTTime() {
		return get(ttime);
	}

	public void setTTime(final Time tshort) {
		set(Pizza.ttime, tshort);
	}

	public Timestamp getTTimestamp() {
		return get(ttimestamp);
	}

	public void setTTimestamp(final Timestamp tshort) {
		set(Pizza.ttimestamp, tshort);
	}

}
