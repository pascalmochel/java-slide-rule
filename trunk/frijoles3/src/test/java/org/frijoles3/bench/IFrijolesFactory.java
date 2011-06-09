package org.frijoles3.bench;

import java.awt.Color;

import org.frijoles3.anno.Scope;
import org.frijoles3.holder.impl.Prototype;

public interface IFrijolesFactory {

	@Scope(Prototype.class)
	ColorsMap getColorsMap(final IFrijolesFactory f);

	@Scope(Prototype.class)
	Color getColorRed(final IFrijolesFactory f);

	@Scope(Prototype.class)
	Color getColorGreen(final IFrijolesFactory f);

	@Scope(Prototype.class)
	Color getColorBlue(final IFrijolesFactory f);

}
