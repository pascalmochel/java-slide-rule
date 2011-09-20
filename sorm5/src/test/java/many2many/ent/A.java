package many2many.ent;

import org.morm.record.Entity;
import org.morm.record.compo.OneToMany;
import org.morm.record.field.impl.primitive.FLong;
import org.morm.record.identity.IdentityGenerator;
import org.morm.record.identity.impl.hsqldb.HsqldbIdentity;

import java.util.List;

public class A extends Entity {

	public static IdentityGenerator<Long> id = new HsqldbIdentity<Long>(new FLong("ID_A"));

	public static OneToMany<Integer, AB> abs = new OneToMany<Integer, AB>(AB.class, AB.as);

	public A() {
		registerIdField(id);
		registerOneToMany(abs);
	}

	public Long getId() {
		return get(id);
	}

	public void setId(final Long id) {
		set(A.id, id);
	}

	public void setAbs(final List<AB> abs) {
		setCollaboration(A.abs, abs);
	}

	public List<AB> getAbs() {
		return getCollaboration(A.abs);
	}

}
