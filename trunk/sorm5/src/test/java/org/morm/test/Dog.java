package org.morm.test;

import org.morm.criteria.Criterion;
import org.morm.record.Entity;
import org.morm.record.compo.OneToMany;
import org.morm.record.field.impl.FString;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityKeyGenerator;
import org.morm.record.identity.impl.HsqldbIdentity;

import java.util.List;

public class Dog extends Entity {

	public static IdentityKeyGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_DOG"));
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
		return getCollaborations(rabbits);
	}

	public void setRabbits(final List<Rabbit> rabbits) {
		setCollaborations(Dog.rabbits, rabbits);
	}

	public void setAge(final Integer age) {
		set(Dog.age, age);
	}

	protected static Dog X = new Dog();

	public static Dog findById(final Integer id) {
		return X.loadById(id);
	}

	public static List<Entity> findAll() {
		return X.loadAll();
	}

	public static List<Dog> findBy(final Criterion... criterions) {
		return X.loadBy(criterions);
	}

}
