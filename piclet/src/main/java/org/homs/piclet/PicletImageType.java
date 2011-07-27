package org.homs.piclet;

import java.awt.image.BufferedImage;

public enum PicletImageType {

	RGB(BufferedImage.TYPE_INT_RGB), //
	ARGB(BufferedImage.TYPE_INT_ARGB), //
	ARGB_PRE(BufferedImage.TYPE_INT_ARGB_PRE), //
	BGR(BufferedImage.TYPE_INT_BGR) //
	;

	private final int type;

	private PicletImageType(final int type) {
		this.type = type;
	}

	public int getImageType() {
		return type;
	}

}
