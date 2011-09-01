package org.morm.record;

import org.morm.record.field.impl.FString;
import org.morm.record.field.impl.primitive.FInteger;

public class Rabbit extends Entity {

	public static FInteger id = new FInteger("ID_RABBIT");
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public Rabbit() {
		setTableName("RABBIT");
		registerIdField(id);
		registerFields(name, age);
	}

	public Rabbit(final Integer id, final String name, Integer age) {
		this();
		setId(id);
		setName(name);
		setAge(age);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(Rabbit.id, id);
	}

	public String getName() {
		return get(name);
	}

	public void setName(final String name) {
		set(Rabbit.name, name);
	}

	public Integer getAge() {
		return get(age);
	}

	public void setAge(final Integer age) {
		set(Rabbit.age, age);
	}

}
