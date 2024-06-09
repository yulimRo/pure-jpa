package com.orm.pure.jpa.ex07.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    @Column
    public String city;
    public String street;
    public String zipcode;

}
