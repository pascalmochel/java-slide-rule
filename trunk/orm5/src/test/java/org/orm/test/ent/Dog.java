package org.orm.test.ent;

import org.orm.record.Entity;
import org.orm.record.field.compo.OneToMany;
import org.orm.record.field.identity.IdentityGenerator;
import org.orm.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.orm.record.field.regular.FString;
import org.orm.record.field.regular.primitive.FInteger;

import java.util.List;

//TODO implementar un generador d'entities?
public class Dog extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_DOG"));
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public static OneToMany<Integer, Rabbit> rabbits = new OneToMany<Integer, Rabbit>(Rabbit.class,
			Rabbit.dog);

	public Dog() {
		setTableName("DOG");
		registerIdField(id);
		registerFields(name, age);
		registerOneToMany(rabbits);
	}

	public Dog(final Integer id, final String name, final Integer age) {
		this();
		setId(id);
		setName(name);
		setAge(age);
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

	public Integer getAge() {
		return get(age);
	}

	public List<Rabbit> getRabbits() {
		return getCollaboration(rabbits);
	}

	public void setRabbits(final List<Rabbit> rabbits) {
		setCollaboration(Dog.rabbits, rabbits);
	}

	public void setAge(final Integer age) {
		set(Dog.age, age);
	}

}
