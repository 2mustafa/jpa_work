package edu.damago.cookbook.persistence;

import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Embeddable
public class Address implements Comparable<Address> {
	static private final Comparator<Address> COMPARATOR = Comparator.comparing(Address::getCountry).thenComparing(Address::getCity).thenComparing(Address::getStreet).thenComparing(Address::getPostcode);

	//postcode VARCHAR(15) NOT NULL,
	@NotNull
	@Size(max = 15)
	@Column(name = "postcode", nullable = false, updatable = true, insertable = true, length = 15)
	private String postcode;

	//street VARCHAR(63) NOT NULL,
	@NotNull
	@Size(max = 63)
	@Column(name = "street", nullable = false, updatable = true, insertable = true, length = 63)
	private String street;

	//city VARCHAR(63) NOT NULL,
	@NotNull
	@Size(max = 63)
	@Column(name = "city", nullable = false, updatable = true, insertable = true, length = 63)
	private String city;

	//country VARCHAR(63) NOT NULL,
	@NotNull
	@Size(max = 63)
	@Column(name = "country", nullable = false, updatable = true, insertable = true, length = 63)
	private String country;


	public String getPostcode () {
		return postcode;
	}


	public void setPostcode (String postcode) {
		this.postcode = postcode;
	}


	public String getStreet () {
		return street;
	}


	public void setStreet (String street) {
		this.street = street;
	}


	public String getCity () {
		return city;
	}


	public void setCity (String city) {
		this.city = city;
	}


	public String getCountry () {
		return country;
	}


	public void setCountry (String country) {
		this.country = country;
	}


	@Override
	public int compareTo (final Address other) {
		return COMPARATOR.compare(this, other);
	}


	@Override
	public String toString () {
		return String.format("(street=%s, city=%s, country=%s, postcode=%s)", this.street, this.city, this.country, this.postcode);
	}

}
