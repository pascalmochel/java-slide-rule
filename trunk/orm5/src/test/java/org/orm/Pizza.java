//package org.mb;
//
//import org.mb.record.Entity;
//import org.mb.record.config.Config;
//import org.mb.record.config.ConfigurableEntity;
//import org.mb.record.field.identity.IdentityGenerator;
//import org.mb.record.field.identity.impl.hsqldb.HsqldbIdentity;
//import org.mb.record.field.regular.FString;
//import org.mb.record.field.regular.primitive.FInteger;
//
//public class Pizza extends Entity {
//
//	public static IdentityGenerator<Integer> id = new HsqldbIdentity<Integer>(new FInteger("ID_PIZZA"));
//	public static FString name = new FString("NAME");
//	public static FInteger price = new FInteger("PRICE");
//
//	// XXX aquí, estàticament s'assignen els índexos de camp
//	public static final Config c;
//	static {
//		c = new ConfigurableEntity()
//		/**/.registerIdField(id)
//		/**/.registerFields(name, price)
//		/**/.configure();
//	}
//
//	public Pizza() {
//		// XXX aquí es passa la config a la Entity, i es clonen els fields
//		super(c/* .doClone() */);
//		// // super.setConfig(c);
//		//
//		// setTableName("PIZZA");
//		// registerIdField(id);
//		// registerFields(name, price);
//	}
//
//	public Pizza(final Integer id, final String name, final Integer price) {
//		this();
//		setId(id);
//		setName(name);
//		setPrice(price);
//	}
//
//	public Integer getId() {
//		return get(id);
//	}
//
//	public void setId(final Integer id) {
//		set(Pizza.id, id);
//	}
//
//	public String getName() {
//		return get(name);
//	}
//
//	public void setName(final String name) {
//		set(Pizza.name, name);
//	}
//
//	public Integer getPrice() {
//		return get(price);
//	}
//
//	public void setPrice(final Integer price) {
//		set(Pizza.price, price);
//	}
//
//}
