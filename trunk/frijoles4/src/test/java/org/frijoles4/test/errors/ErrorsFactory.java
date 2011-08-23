//package org.frijoles4.test.errors;
//
//import javax.servlet.http.HttpSession;
//
//import org.frijoles4.FrijolesContext;
//import org.frijoles4.anno.Scope;
//import org.frijoles4.scope.impl.Request;
//import org.frijoles4.scope.impl.Session;
//
//public class ErrorsFactory {
//
//	@Scope
//	public Integer method1() {
//		return Integer.valueOf(1);
//	}
//
//	@Scope
//	public Integer method2(final FrijolesContext ctx) {
//		return Integer.valueOf(2);
//	}
//
//	@Scope
//	public Integer method3(final FrijolesContext ctx, final int i) {
//		return Integer.valueOf(3);
//	}
//
//	@Scope(Session.class)
//	public Integer method4(final FrijolesContext ctx, final int i) {
//		return Integer.valueOf(4);
//	}
//
//	@Scope(Session.class)
//	public Integer method5(final FrijolesContext ctx, final HttpSession session, final int i) {
//		return Integer.valueOf(5);
//	}
//
//	@Scope(Request.class)
//	public Integer method6(final FrijolesContext ctx, final HttpSession session, final int i) {
//		return Integer.valueOf(6);
//	}
//
//	@Scope(Request.class)
//	public Integer method7(final FrijolesContext ctx, final HttpSession session, final int i) {
//		return Integer.valueOf(7);
//	}
//
// }
