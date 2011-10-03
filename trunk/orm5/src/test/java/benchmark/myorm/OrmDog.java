package benchmark.myorm;

import org.orm.record.Entity;
import org.orm.record.field.impl.FString;
import org.orm.record.field.impl.primitive.FInteger;
import org.orm.record.identity.IdentityGenerator;
import org.orm.record.identity.impl.hsqldb.HsqldbSequence;
import org.orm.test.ent.Dog;

public class OrmDog extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbSequence<Integer>(new FInteger("ID_DOG"),
			"DOG_SEQUENCE");
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public OrmDog() {
		setTableName("DOG");
		registerIdField(id);
		registerFields(name, age);
	}

	public OrmDog(final Integer id, final String name, final Integer age) {
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

	public void setAge(final Integer age) {
		set(OrmDog.age, age);
	}

}
