package org.morm.test;

import org.morm.record.Entity;
import org.morm.record.compo.ManyToOne;
import org.morm.record.field.impl.FString;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityGenerator;
import org.morm.record.identity.impl.hsqldb.HsqldbIdentity;

public class Rabbit extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_RABBIT"));
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public static ManyToOne<Integer, Dog> dog = new ManyToOne<Integer, Dog>(new FInteger("NUM_DOG"),
			Dog.class);

	public Rabbit() {
		setTableName("RABBIT");
		registerIdField(id);
		registerFields(name, age);
		registerManyToOne(dog);
	}

	public Rabbit(final Integer id, final String name, final Integer age) {
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

	public Dog getDog() {
		return getCollaboration(dog);
	}

	public void setDog(final Dog d) {
		setCollaboration(dog, d);
	}

}
