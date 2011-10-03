package org.orm.record.field.impl;

import org.orm.record.Entity;
import org.orm.record.field.impl.FEnum;
import org.orm.record.field.impl.FString;
import org.orm.record.field.impl.primitive.FInteger;
import org.orm.record.identity.IdentityGenerator;
import org.orm.record.identity.impl.hsqldb.HsqldbIdentity;
import org.orm.test.ent.Dog;

public class EnumDog extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_DOG"));
	public static FString name = new FString("NAME");
	public static FEnum<EColor> color = new FEnum<EColor>("COLOR", EColor.class);

	public EnumDog() {
		setTableName("DOG");
		registerIdField(id);
		registerFields(name, color);
	}

	public EnumDog(final Integer id, final String name, final EColor color) {
		this();
		setId(id);
		setName(name);
		setColor(color);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(Dog.id, id);
	}

	public String getName() {
		return get(name);
	}

	public void setName(final String name) {
		set(Dog.name, name);
	}

	public EColor getColor() {
		return getEnum(color);
	}

	public void setColor(final EColor color) {
		setEnum(EnumDog.color, color);
	}

}
