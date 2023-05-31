package edu.damago.cookbook.persistence;

import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Name implements Comparable<Name> {
	static private final Comparator<Name> COMPARATOR = Comparator.comparing(Name::getTitle, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(Name::getFamily).thenComparing(Name::getGiven);

	//title VARCHAR(15) NULL,
	@Size(max = 15)
	@Column(name = "title", nullable = true, updatable = true, insertable = true, length = 15)
	private String title;

	//surname VARCHAR(31) NOT NULL,
	@NotNull
	@Size(max = 31)
	@Column(name = "surname", nullable = false, updatable = true, insertable = true, length = 31)
	private String family;

	//forename VARCHAR(31) NOT NULL,
	@NotNull
	@Size(max = 31)
	@Column(name = "forename", nullable = false, updatable = true, insertable = true, length = 31)
	private String given;


	public String getTitle () {
		return title;
	}


	public void setTitle (String title) {
		this.title = title;
	}


	public String getFamily () {
		return family;
	}


	public void setFamily (String family) {
		this.family = family;
	}


	public String getGiven () {
		return given;
	}


	public void setGiven (String given) {
		this.given = given;
	}


	@Override
	public int compareTo (final Name other) {
		return COMPARATOR.compare(this, other);
	}


	@Override
	public String toString () {
		return String.format("(title=%s, family=%s, given=%s)", this.title, this.family, this.given);
	}

}
