package org.frijoles4.exception;

public class BestDistanceAliasFinder {

	public static String find(final String wrongAlias, final String[] aliases) {

		String bestAlias = null;
		int bestDist = Integer.MAX_VALUE;

		for (final String alias : aliases) {
			final int dist = computeLevenshteinDistance(wrongAlias.toLowerCase(), alias.toLowerCase());
			if (dist < bestDist) {
				bestAlias = alias;
				bestDist = dist;
			}
		}
		return bestAlias;
	}

	protected static int minimum(final int a, final int b, final int c) {
		if (a <= b && a <= c) {
			return a;
		}
		if (b <= a && b <= c) {
			return b;
		}
		return c;
	}

	protected static int computeLevenshteinDistance(final String s1, final String s2) {
		final char[] str1 = s1.toCharArray();
		final char[] str2 = s2.toCharArray();
		final int[][] distance = new int[str1.length + 1][str2.length + 1];

		for (int i = 0; i <= str1.length; i++) {
			distance[i][0] = i;
		}

		for (int j = 0; j <= str2.length; j++) {
			distance[0][j] = j;
		}

		for (int i = 1; i <= str1.length; i++) {
			for (int j = 1; j <= str2.length; j++) {
				distance[i][j] = minimum(
				/**/distance[i - 1][j] + 1,
				/**/distance[i][j - 1] + 1,
				/**/distance[i - 1][j - 1] + ((str1[i - 1] == str2[j - 1]) ? 0 : 1));
			}
		}

		return distance[str1.length][str2.length];
	}

}
