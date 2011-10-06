package org.orm.test.ent;

import org.orm.record.Entity;
import org.orm.record.field.compo.ManyToOne;
import org.orm.record.field.identity.IdentityGenerator;
import org.orm.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.orm.record.field.regular.FString;
import org.orm.record.field.regular.primitive.FInteger;

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
		registerManyToOnes(dog);
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
