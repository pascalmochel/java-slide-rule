package org.frijoles3.test.di;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;
import org.frijoles3.holder.impl.Singleton;
import org.frijoles3.test.ents.Dog;
import org.frijoles3.test.ents.Person;

public interface IDiFactory {

	@Scope(Prototype.class)
	Person getPerson1(IDiFactory self);

	@Scope(Singleton.class)
	Dog getDog1(IDiFactory self);

	@Scope(Prototype.class)
	Person getPerson2(IDiFactory self);

	@Scope(Prototype.class)
	Dog getDog2(IDiFactory self);

}