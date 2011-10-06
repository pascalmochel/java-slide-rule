package org.orm;

import org.orm.record.Entity;
import org.orm.record.field.identity.IdentityGenerator;
import org.orm.record.field.identity.impl.hsqldb.HsqldbIdentity;
import org.orm.record.field.regular.FString;
import org.orm.record.field.regular.primitive.FInteger;

public class Pizza extends Entity {

	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_PIZZA"));
	public static FString name = new FString("NAME");
	public static FInteger price = new FInteger("PRICE");

	public Pizza() {
		// final Config c = new
		// IConfigurableEntity().registerIdField(id).registerFields(name,
		// price).configure();
		// super.setConfig(c);

		setTableName("PIZZA");
		registerIdField(id);
		registerFields(name, price);
	}

	public Pizza(final Integer id, final String name, final Integer price) {
		this();
		setId(id);
		setName(name);
		setPrice(price);
	}

	public Integer getId() {
		return get(id);
	}

	public void setId(final Integer id) {
		set(Pizza.id, id);
	}

	public String getName() {
		return get(name);
	}

	public void setName(final String name) {
		set(Pizza.name, name);
	}

	public Integer getPrice() {
		return get(price);
	}

	public void setPrice(final Integer price) {
		set(Pizza.price, price);
	}

}
