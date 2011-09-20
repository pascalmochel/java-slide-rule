package many2many.ent;

import java.util.List;

import org.morm.record.Entity;
import org.morm.record.compo.OneToMany;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityGenerator;
import org.morm.record.identity.impl.hsqldb.HsqldbIdentity;

public class A extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_A"));

	public static OneToMany<Integer, AB> abs = new OneToMany<Integer, AB>(AB.class, AB.as);

	public A() {
		registerIdField(id);
		registerOneToMany(abs);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(A.id, id);
	}

	public void setAbs(final List<AB> abs) {
		setCollaboration(A.abs, abs);
	}

	public List<AB> getAbs() {
		return getCollaboration(A.abs);
	}

}
