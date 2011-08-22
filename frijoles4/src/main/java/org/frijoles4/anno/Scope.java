package org.frijoles4.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.frijoles4.scope.ScopedBean;
import org.frijoles4.scope.impl.Singleton;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scope {

	Class<? extends ScopedBean> value() default Singleton.class;
}
