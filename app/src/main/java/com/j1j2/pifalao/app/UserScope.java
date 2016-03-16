package com.j1j2.pifalao.app;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by alienzxh on 16-3-5.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}
