package many2many.ent;

import org.morm.record.Entity;
import org.morm.record.compo.OneToMany;
import org.morm.record.field.impl.FString;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityGenerator;
import org.morm.record.identity.impl.hsqldb.HsqldbIdentity;
import org.morm.test.Dog;

import java.util.List;

public class A extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_A"));
	public static FString name = new FString("NAME");

	public static OneToMany<Integer, AB> abs = new OneToMany<Integer, AB>(AB.class, AB.as);

	public A() {
		registerIdField(id);
		registerFields(name);
		registerOneToMany(abs);
	}

	public A(final String name) {
		this();
		setName(name);
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

	public void setAbs(final List<AB> abs) {
		setCollaborations(A.abs, abs);
	}

	public List<AB> getAbs() {
		return getCollaborations(A.abs);
	}

}
