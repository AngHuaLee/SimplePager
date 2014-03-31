package com.google.gwt.sample.dynatable.shared.vo.ofy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OfyWhiteList {
	String[] authorizedAccounts() default {};
	String[] authorizedDomains() default {};
}
