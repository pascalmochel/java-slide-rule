package org.morm.test;

import org.morm.criteria.Criterion;
import org.morm.record.Entity;
import org.morm.record.field.impl.FString;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityKeyGenerator;
import org.morm.record.identity.impl.HsqldbIdentity;

import java.util.List;

public class Rabbit extends Entity {

	public static IdentityKeyGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_RABBIT"));
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public Rabbit() {
		setTableName("RABBIT");
		registerIdField(id);
		registerFields(name, age);
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
