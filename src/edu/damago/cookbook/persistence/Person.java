package edu.damago.cookbook.persistence;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.CacheIndex;
import edu.damago.tool.HashCodes;
import edu.damago.cookbook.persistence.BaseEntity;


@Entity
@Table(schema = "cookbook", name = "Person")
@PrimaryKeyJoinColumn(name = "personIdentity")
@DiscriminatorValue("Person")
public class Person extends BaseEntity {

	static public enum Group {
		USER, ADMIN
	}


	static private final String DEFAULT_PASSWORD = "changeit";
	static private final String DEFAULT_PASSWORD_HASH = HashCodes.sha2HashText(256, DEFAULT_PASSWORD);

	//avatarReference BIGINT NOT NULL,
	//FOREIGN KEY (avatarReference) REFERENCES Document (documentIdentity) ON DELETE RESTRICT ON UPDATE CASCADE, //update == primärschlüssel
	@ManyToOne(optional = false)
	@JoinColumn(name = "avatarReference", nullable = false, updatable = true)
	private Document avatar;

	//email CHAR(128) NOT NULL,
	//UNIQUE KEY (email)
	@NotNull
	@Email
	@Size(max = 128)
	@CacheIndex(updateable = false)
	@Column(name = "email", nullable = false, updatable = true, unique = true, length = 128)
	private String email;

	//passwordHash CHAR(64) NOT NULL,
	@NotNull
	@Size(max = 64)
	@Column(name = "passwordHash", nullable = false, updatable = true, length = 64)
	private String passwordHash;

	//groupAlias ENUM("USER", "ADMIN") NOT NULL,
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "groupAlias", nullable = false, updatable = true)
	private Group group;

	@NotNull @Valid
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "family", column = @Column(name = "surname")),
		@AttributeOverride(name = "given", column = @Column(name = "forename"))
	})
	private Name name;

	@Embedded
	@NotNull
	@Valid
	private Address address;

	@NotNull
	@ElementCollection
	@CollectionTable(
		schema = "Cookbook",
		name = "PhoneAssociation",
		joinColumns = @JoinColumn(name = "personReference", nullable = false, updatable = true),
		uniqueConstraints = @UniqueConstraint(columnNames = { "personReference", "phone" })
	)
	@Column(name = "phone", nullable = false, updatable = false, insertable = true, length = 16)
	protected HashSet<String> phones;

	@NotNull
	//@Column(name = "recipes", nullable = false, updatable = false, length = 16)
	@OneToMany(mappedBy = "owner", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
	private Set<Recipe> recipes;


	public Person () {
		this.passwordHash = DEFAULT_PASSWORD_HASH;
		this.group = Group.USER;
		this.name = new Name();
		this.address = new Address();
		this.phones = new HashSet<>();
		this.recipes = Collections.emptySet();

	}


	public Document getAvatar () {
		return avatar;
	}


	public void setAvatar (Document avatar) {
		this.avatar = avatar;
	}


	public String getEmail () {
		return email;
	}


	public void setEmail (String email) {
		this.email = email;
	}


	public String getPasswordHash () {
		return passwordHash;
	}


	public void setPasswordHash (String passwordHash) {
		this.passwordHash = passwordHash;
	}


	public Group getGroup () {
		return group;
	}


	public void setGroup (Group group) {
		this.group = group;
	}


	public Name getName () {
		return name;
	}


	public void setName (Name name) {
		this.name = name;
	}


	public Address getAddress () {
		return address;
	}


	public void setAddress (Address address) {
		this.address = address;
	}


	public HashSet<String> getPhones () {
		return phones;
	}


	public void setPhones (HashSet<String> phones) {
		this.phones = phones;
	}


	public Set<Recipe> getRecipes () {
		return recipes;
	}


	public void setIngredient (Set<Recipe> recipes) {
		this.recipes = recipes;
	}

}
