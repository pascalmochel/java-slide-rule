package many2many.ent;

import org.orm.record.Entity;
import org.orm.record.compo.OneToMany;
import org.orm.record.field.impl.primitive.FLong;
import org.orm.record.identity.IdentityGenerator;
import org.orm.record.identity.impl.hsqldb.HsqldbIdentity;

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
