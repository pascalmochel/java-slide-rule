package org.frijoles3.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.frijoles3.holder.AbstractHolder;
import org.frijoles3.holder.impl.Singleton;

//XXX eagerly factory
//XXX si Scope-annotation està en interface i en impl, que faci cas a la impl!
//XXX facilitar properties en factory, per fer com en Spring:
//<prop key="hibernate.dialect">${hibernate.dialect}</prop>

// TODO fer un bean inaccessible directament: @Scope(Singleton.class,
// private=true)
//TODO obtenir beans per string
//TODO a part, fer un fluent validator? 
//TODO testar un bench amb un altre container
//TODO dotar els beans de cicle de vida: initialize/dispose
//TODO estalvi d'interface per asm/cglib

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scope {

	Class<? extends AbstractHolder> value() default Singleton.class;

	// String alias() default "";

}