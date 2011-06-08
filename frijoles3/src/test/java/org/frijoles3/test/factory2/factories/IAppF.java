package org.frijoles3.test.factory2.factories;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;
import org.frijoles3.holder.impl.Singleton;
import org.frijoles3.test.ents.Dog;
import org.frijoles3.test.ents.Person;

public interface IAppF {

	@Scope(Prototype.class)
	Person getPerson(IAppF self);

	@Scope(Singleton.class)
	Dog getDog(IAppF self);

}