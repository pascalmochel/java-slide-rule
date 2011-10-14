package many2many.ent;

import org.mb.record.Entity;
import org.mb.record.field.compo.OneToMany;
import org.mb.record.field.identity.IdentityGenerator;
import org.mb.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.mb.record.field.regular.primitive.FInteger;

import java.util.List;

public class B extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_B"));

	public static OneToMany<Integer, AB> abs = new OneToMany<Integer, AB>(AB.class, AB.bs);

	public B() {
		registerIdField(id);
		registerOneToMany(abs);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(B.id, id);
	}

	public void setAbs(final List<AB> abs) {
		setCollaboration(B.abs, abs);
	}

	public List<AB> getAbs() {
		return getCollaboration(B.abs);
	}

}
