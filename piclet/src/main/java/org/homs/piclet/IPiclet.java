package org.homs.piclet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IPiclet {

	void doDownload(final HttpServletRequest request, final HttpServletResponse response);

}
