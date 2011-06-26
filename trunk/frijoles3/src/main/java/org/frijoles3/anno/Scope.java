package org.frijoles3.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.frijoles3.holder.AbstractHolder;
import org.frijoles3.holder.impl.Singleton;

// TODO fer un bean inaccessible directament: @Scope(Singleton.class,
// private=true)

// TODO fer com spring InitializingBean, que verifiqui les injeccions

// XXX facilitar properties en factory, per fer com en Spring:
//<prop key="hibernate.dialect">${hibernate.dialect}</prop>

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scope {

	Class<? extends AbstractHolder> value() default Singleton.class;

}