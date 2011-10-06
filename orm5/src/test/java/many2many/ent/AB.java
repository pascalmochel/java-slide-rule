package many2many.ent;

import org.orm.record.Entity;
import org.orm.record.field.compo.ManyToOne;
import org.orm.record.field.identity.IdentityGenerator;
import org.orm.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.orm.record.field.regular.primitive.FInteger;
import org.orm.record.field.regular.primitive.FLong;

public class AB extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_AB"));

	public static ManyToOne<Long, A> as = new ManyToOne<Long, A>(new FLong("ID_A"), A.class);
	public static ManyToOne<Integer, B> bs = new ManyToOne<Integer, B>(new FInteger("ID_B"), B.class);

	public AB() {
		registerIdField(id);
		registerManyToOnes(as, bs);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(AB.id, id);
	}

	public void setA(final A a) {
		setCollaboration(AB.as, a);
	}

	public A getA() {
		return getCollaboration(AB.as);
	}

	public void setB(final B b) {
		setCollaboration(AB.bs, b);
	}

	public B getB() {
		return getCollaboration(AB.bs);
	}

}
