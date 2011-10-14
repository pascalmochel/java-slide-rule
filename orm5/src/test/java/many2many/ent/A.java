package many2many.ent;

import org.mb.record.Entity;
import org.mb.record.field.compo.OneToMany;
import org.mb.record.field.identity.IdentityGenerator;
import org.mb.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.mb.record.field.regular.primitive.FLong;

import java.util.List;

public class A extends Entity {

	public static IdentityGenerator<Long> id = new HsqldbIdentity<Long>(new FLong("ID_A"));

	public static OneToMany<Long, AB> abs = new OneToMany<Long, AB>(AB.class, AB.as);

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
