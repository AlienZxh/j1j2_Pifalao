package com.j1j2.pifalao.app.event;

import com.j1j2.data.model.Address;

/**
 * Created by alienzxh on 16-4-10.
 */
public class UserAddressSelectEvent {

    private final Address address;

    public UserAddressSelectEvent(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
