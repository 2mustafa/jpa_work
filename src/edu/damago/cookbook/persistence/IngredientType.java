package edu.damago.cookbook.persistence;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(schema = "cookbook", name = "IngredientType")
@PrimaryKeyJoinColumn(name = "ingredientTypeIdentity")
@DiscriminatorValue("IngredientType")
public class IngredientType extends BaseEntity {

	@NotNull
	//@ManyToOne
	@JoinColumn(name = "avatarReference", nullable = false, updatable = true, insertable = true)
	private Document avatar;

	@NotNull
	@Size(max = 128)
	@JoinColumn
	@Column(nullable = false, updatable = false, insertable = true, length = 128, unique = true)
	private String alias;

	@NotNull
	@Column(nullable = false, updatable = true, insertable = true)
	private boolean pescatarian;

	@NotNull
	@Column(nullable = false, updatable = true, insertable = true)
	private boolean lactoOvoVegetarian;

	@NotNull
	@Column(nullable = false, updatable = true, insertable = true)
	private boolean lactoVegetarian;

	@NotNull
	@Column(nullable = false, updatable = true, insertable = true)
	private boolean vegan;

	@Size(max = 4094)
	@Column(nullable = true, updatable = true, insertable = true, length = 4094)
	private String description;


	public IngredientType () {

	}

	//	public IngredientType (Document avatar, String alias, String description, boolean pescatarian, boolean lactoOvoVegetarian, boolean lactoVegetarian, boolean vegan) {
	//		this.avatar = avatar;
	//		this.alias = alias;
	//		this.description = description;
	//		this.pescatarian = pescatarian;
	//		this.lactoOvoVegetarian = lactoOvoVegetarian;
	//		this.lactoVegetarian = lactoVegetarian;
	//		this.vegan = vegan;
	//	}


	public Document getAvatar () {
		return avatar;
	}


	public void setAvatar (Document parameterization) {
		this.avatar = parameterization;
	}


	public String getAlias () {
		return alias;
	}


	public void setAlias (String parameterization) {
		this.alias = parameterization;
	}


	public boolean isPescatarian () {
		return pescatarian;
	}


	public void setPescatarian (boolean parameterization) {
		System.out.println("starting setPescatarian " + parameterization);
		if (parameterization == true) {
			this.pescatarian = true;
			System.out.println("isPescatarian " + isPescatarian());
			this.lactoOvoVegetarian = false;
			System.out.println("isLactoOvoVegetarian " + isLactoOvoVegetarian());
			this.lactoVegetarian = false;
			System.out.println("isLactoVegetarian " + isLactoVegetarian());
			this.vegan = false;
			System.out.println("isVegan " + isVegan());

			System.out.println("setPescatarian done...");
		} else if (parameterization == false) {
			this.pescatarian = false;
			System.out.println("isPescatarian " + isPescatarian());
			this.lactoOvoVegetarian = false;
			System.out.println("isLactoOvoVegetarian " + isLactoOvoVegetarian());
			this.lactoVegetarian = false;
			System.out.println("isLactoVegetarian " + isLactoVegetarian());
			this.vegan = false;
			System.out.println("isVegan " + isVegan());

			System.out.println("setPescatarian done...");
		}

	}


	public boolean isLactoOvoVegetarian () {
		return lactoOvoVegetarian;
	}


	public void setLactoOvoVegetarian (boolean parameterization) {
		System.out.println("starting setLactoOvoVegetarian " + parameterization);
		if (parameterization == true) {
			this.pescatarian = true;
			System.out.println("isPescatarian " + isPescatarian());
			this.lactoOvoVegetarian = true;
			System.out.println("isLactoOvoVegetarian " + isLactoOvoVegetarian());
			this.lactoVegetarian = false;
			System.out.println("isLactoVegetarian " + isLactoVegetarian());
			this.vegan = false;
			System.out.println("isVegan " + isVegan());

			System.out.println("setLactoOvoVegetarian done...");
		} else if (parameterization == false) {
			this.pescatarian = false;
			System.out.println("isPescatarian " + isPescatarian());
			this.lactoOvoVegetarian = false;
			System.out.println("isLactoOvoVegetarian " + isLactoOvoVegetarian());
			this.lactoVegetarian = true;
			System.out.println("isLactoVegetarian " + isLactoVegetarian());
			this.vegan = true;
			System.out.println("isVegan " + isVegan());

			System.out.println("setLactoOvoVegetarian done...");
		}
	}


	public boolean isLactoVegetarian () {
		return lactoVegetarian;
	}


	public void setLactoVegetarian (boolean parameterization) {
		System.out.println("starting setLactoVegetarian " + parameterization);
		if (parameterization == true) {
			this.pescatarian = true;
			System.out.println("isPescatarian " + isPescatarian());
			this.lactoOvoVegetarian = true;
			System.out.println("isLactoOvoVegetarian " + isLactoOvoVegetarian());
			this.lactoVegetarian = true;
			System.out.println("isLactoVegetarian " + isLactoVegetarian());
			this.vegan = false;
			System.out.println("isVegan " + isVegan());

			System.out.println("setLactoVegetarian done...");
		} else if (parameterization == false) {
			this.pescatarian = false;
			System.out.println("isPescatarian " + isPescatarian());
			this.lactoOvoVegetarian = false;
			System.out.println("isLactoOvoVegetarian " + isLactoOvoVegetarian());
			this.lactoVegetarian = false;
			System.out.println("isLactoVegetarian " + isLactoVegetarian());
			this.vegan = false;
			System.out.println("isVegan " + isVegan());

			System.out.println("setLactoVegetarian done...");
		}
	}


	public boolean isVegan () {
		return vegan;
	}


	public void setVegan (boolean parameterization) {
		System.out.println("starting setVegan " + parameterization);
		if (parameterization == true) {
			this.pescatarian = true;
			System.out.println("isPescatarian " + isPescatarian());
			this.lactoOvoVegetarian = true;
			System.out.println("isLactoOvoVegetarian " + isLactoOvoVegetarian());
			this.lactoVegetarian = true;
			System.out.println("isLactoVegetarian " + isLactoVegetarian());
			this.vegan = true;
			System.out.println("isVegan " + isVegan());

			System.out.println("setVegan done...");
		} else if (parameterization == false) {
			this.pescatarian = false;
			System.out.println("isPescatarian " + isPescatarian());
			this.lactoOvoVegetarian = false;
			System.out.println("isLactoOvoVegetarian " + isLactoOvoVegetarian());
			this.lactoVegetarian = false;
			System.out.println("isLactoVegetarian " + isLactoVegetarian());
			this.vegan = false;
			System.out.println("isVegan " + isVegan());

			System.out.println("setVegan done...");
		}
	}


	public String getDescription () {
		return description;
	}


	public void setDescription (String description) {
		this.description = description;
	}

}
