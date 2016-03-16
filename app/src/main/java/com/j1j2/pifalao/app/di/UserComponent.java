package com.j1j2.pifalao.app.di;

import com.j1j2.pifalao.app.UserScope;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-11.
 */
@UserScope
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {
}
