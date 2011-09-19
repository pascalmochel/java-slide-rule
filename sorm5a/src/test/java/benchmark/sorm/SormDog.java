package benchmark.sorm;

import org.morm.record.Entity;
import org.morm.record.field.impl.FString;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityGenerator;
import org.morm.record.identity.impl.hsqldb.HsqldbSequence;
import org.morm.test.Dog;

public class SormDog extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbSequence<Integer>(new FInteger("ID_DOG"),
			"DOG_SEQUENCE");
	public static FString name = new FString("NAME");
	public static FInteger age = new FInteger("AGE");

	public SormDog() {
		setTableName("DOG");
		registerIdField(id);
		registerFields(name, age);
	}

	public SormDog(final Integer id, final String name, final Integer age) {
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
		set(SormDog.age, age);
	}

}
