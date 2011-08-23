package org.frijoles4.exception;

public class AliasNotDefinedException extends FrijolesException {

	private static final long serialVersionUID = 1L;

	public AliasNotDefinedException(final String wrongAlias, final String[] aliases) {
		super("alias not defined: : '" + wrongAlias + "'; did you mean: '"
				+ BestDistanceAliasFinder.find(wrongAlias, aliases) + "' ?");
	}

}
