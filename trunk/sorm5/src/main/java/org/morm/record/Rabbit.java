package org.morm.record;

import org.morm.record.field.impl.FInteger;
import org.morm.record.field.impl.FString;

public class Rabbit extends Entity {

	public static FInteger id = new FInteger("ID_RABBIT");
	public static FString name = new FString("NAME");

	public Rabbit() {
		registerId(id);
		register(name);
	}

	public Rabbit(final Integer id, final String name) {
		this();
		setId(id);
		setName(name);
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

}
