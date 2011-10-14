package org.orm.antipojo;

import org.mb.record.Entity;
import org.mb.record.field.identity.IdentityGenerator;
import org.mb.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.mb.record.field.regular.FString;
import org.mb.record.field.regular.primitive.FInteger;

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
