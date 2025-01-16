package project.post.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String zip;
    private String street;


    public Address() {
    }

    public Address(String city, String zip, String street) {
        this.city = city;
        this.zip = zip;
        this.street = street;
    }
}
