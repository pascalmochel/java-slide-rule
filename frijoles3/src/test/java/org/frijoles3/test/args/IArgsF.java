package org.frijoles3.test.args;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Singleton;
import org.frijoles3.test.ents.Person;

public interface IArgsF {

	@Scope(Singleton.class)
	Person getPerson();

	@Scope(Singleton.class)
	Person getPerson(IArgsF self);

	@Scope(Singleton.class)
	Person getPerson(IArgsF self, String name);
}
