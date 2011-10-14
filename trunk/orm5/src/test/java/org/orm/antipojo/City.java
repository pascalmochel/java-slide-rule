package org.orm.antipojo;

import org.orm.record.Entity;
import org.orm.record.field.identity.IdentityGenerator;
import org.orm.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.orm.record.field.regular.FString;
import org.orm.record.field.regular.primitive.FInteger;

public class City extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_CITY"));
	public static FString name = new FString("NAME");

	// XXX entitat interessant: no és POJO, no té getters/setters
	public City() {
		setTableName("CITY");
		registerIdField(id);
		registerFields(name);
	}

}
