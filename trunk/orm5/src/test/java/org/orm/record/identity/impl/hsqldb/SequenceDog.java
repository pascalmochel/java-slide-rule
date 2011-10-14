package org.orm.record.identity.impl.hsqldb;

import org.mb.record.Entity;
import org.mb.record.field.identity.IdentityGenerator;
import org.mb.record.field.identity.impl.hsqldb.HsqldbSequence;
import org.mb.record.field.regular.FString;
import org.mb.record.field.regular.primitive.FInteger;

public class SequenceDog extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbSequence<Integer>(new FInteger("ID_DOG"),
			"DOG_SEQUENCE");
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public SequenceDog() {
		setTableName("DOG");
		registerIdField(id);
		registerFields(name, age);
	}

	public SequenceDog(final Integer id, final String name, final Integer age) {
		this();
		setId(id);
		setName(name);
		setAge(age);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(SequenceDog.id, id);
	}

	public String getName() {
		return get(name);
	}

	public void setName(final String name) {
		set(SequenceDog.name, name);
	}

	public Integer getAge() {
		return get(age);
	}

	public void setAge(final Integer age) {
		set(SequenceDog.age, age);
	}

}
