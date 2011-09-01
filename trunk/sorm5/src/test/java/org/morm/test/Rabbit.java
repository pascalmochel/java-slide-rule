package org.morm.test;

import org.morm.criteria.Criterion;
import org.morm.record.Entity;
import org.morm.record.compo.ManyToOne;
import org.morm.record.field.impl.FString;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityKeyGenerator;
import org.morm.record.identity.impl.HsqldbIdentity;

import java.util.List;

public class Rabbit extends Entity {

	public static IdentityKeyGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_RABBIT"));
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public static ManyToOne<Integer, Dog> dog = new ManyToOne<Integer, Dog>(new FInteger("ID_DOG"),
			Dog.class, Dog.id);

	public Rabbit() {
		setTableName("RABBIT");
		registerIdField(id);
		registerFields(name, age);
		registerCollaboration(dog);
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

	protected static Rabbit X = new Rabbit();

	public static Rabbit findById(final Integer id) {
		return X.loadById(id);
	}

	public static List<Entity> findAll() {
		return X.loadAll();
	}

	public static List<Rabbit> findBy(final Criterion... criterions) {
		return X.loadBy(criterions);
	}

}
