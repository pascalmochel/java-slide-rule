package org.orm.record.identity.impl;

import org.mb.record.Entity;
import org.mb.record.field.identity.IdentityGenerator;
import org.mb.record.field.identity.impl.NoKeyGenerator;
import org.mb.record.field.regular.FString;
import org.mb.record.field.regular.primitive.FInteger;

public class NoKeyDog extends Entity {

	public static IdentityGenerator<Integer> id = new NoKeyGenerator<Integer>(new FInteger("ID_DOG"));
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public NoKeyDog() {
		setTableName("DOG");
		registerIdField(id);
		registerFields(name, age);
	}

	public NoKeyDog(final Integer id, final String name, final Integer age) {
		this();
		setId(id);
		setName(name);
		setAge(age);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(NoKeyDog.id, id);
	}

	public String getName() {
		return get(name);
	}

	public void setName(final String name) {
		set(NoKeyDog.name, name);
	}

	public Integer getAge() {
		return get(age);
	}

	public void setAge(final Integer age) {
		set(NoKeyDog.age, age);
	}

}
