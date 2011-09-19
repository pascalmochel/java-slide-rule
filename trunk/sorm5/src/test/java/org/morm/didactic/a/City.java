package org.morm.didactic.a;

import org.morm.record.Entity;
import org.morm.record.field.impl.FString;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityGenerator;
import org.morm.record.identity.impl.hsqldb.HsqldbIdentity;

public class City extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_CITY"));
	public static FString name = new FString("NAME");

	public City() {
		setTableName("CITY");
		registerIdField(id);
		registerFields(name);
	}

	public City(final Integer id, final String name, final Integer age) {
		this();
		setId(id);
		setName(name);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(City.id, id);
	}

	public String getName() {
		return get(name);
	}

	public void setName(final String name) {
		set(City.name, name);
	}

}
