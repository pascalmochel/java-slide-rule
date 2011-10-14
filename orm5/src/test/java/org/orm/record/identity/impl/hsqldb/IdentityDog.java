package org.orm.record.identity.impl.hsqldb;

import org.mb.record.Entity;
import org.mb.record.field.identity.IdentityGenerator;
import org.mb.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.mb.record.field.regular.FString;
import org.mb.record.field.regular.primitive.FInteger;

public class IdentityDog extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_DOG"));
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public IdentityDog() {
		setTableName("DOG");
		registerIdField(id);
		registerFields(name, age);
	}

	public IdentityDog(final Integer id, final String name, final Integer age) {
		this();
		setId(id);
		setName(name);
		setAge(age);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(IdentityDog.id, id);
	}

	public String getName() {
		return get(name);
	}

	public void setName(final String name) {
		set(IdentityDog.name, name);
	}

	public Integer getAge() {
		return get(age);
	}

	public void setAge(final Integer age) {
		set(IdentityDog.age, age);
	}

}
