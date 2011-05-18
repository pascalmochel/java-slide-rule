package org.homs.slide.plotter.impl.k;

public enum Label {

	NONE(false, false), INT(true, true), FLOAT(true, false);

	protected final boolean showLabel;
	protected final boolean showAsInt;

	private Label(final boolean showLabel, final boolean showAsInt) {
		this.showLabel = showLabel;
		this.showAsInt = showAsInt;
	}

	public boolean isShowLabel() {
		return showLabel;
	}

	public boolean isShowAsInt() {
		return showAsInt;
	}

}
