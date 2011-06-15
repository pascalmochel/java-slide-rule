package org.frijoles3.bench;

import java.awt.Color;

public interface IFrijolesFactory {

	ColorsMap getColorsMap(final IFrijolesFactory f);

	Color getColorRed(final IFrijolesFactory f);

	Color getColorGreen(final IFrijolesFactory f);

	Color getColorBlue(final IFrijolesFactory f);

}
