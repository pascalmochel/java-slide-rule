package many2many.ent;

import org.morm.record.Entity;
import org.morm.record.compo.ManyToOne;
import org.morm.record.field.impl.primitive.FInteger;
import org.morm.record.identity.IdentityGenerator;
import org.morm.record.identity.impl.hsqldb.HsqldbIdentity;

public class AB extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_AB"));

	public static ManyToOne<Integer, A> as = new ManyToOne<Integer, A>(new FInteger("ID_A"), A.class);
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
