package org.frijoles.binder;

public class Relationals {

	public static State or(final State state1, final State state2) {
		if (state1.isFailed) {
			return state2;
		} else if (state2.isFailed) {
			return state1;
		} else {
			return state1;
		}
	}
	//
	// public static State xor(final State state1, final State state2) {
	// if (!state1.isFailed && state2.isFailed) {
	// return state1;
	// } else if (state1.isFailed && !state2.isFailed) {
	// return state2;
	// } else {
	// if (!state1.isFailed) {
	// return state1;
	// } else /* if (!state2.isFailed) */{
	// return state2;
	// }
	// }
	// }
	//
	// public static State and(final State state1, final State state2) {
	// if (state1.isFailed) {
	// return state1;
	// } else if (state2.isFailed) {
	// return state2;
	// } else {
	// return state1;
	// }
	// }

}