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
public class IngredientType extends Document {

	@NotNull
	@JoinColumn(name = "avatarReference", nullable = false, updatable = false, insertable = true)
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
		this(null, null, null, null, null, null, null);
	}

	public IngredientType (Document avatar, String alias, String description, boolean pescatarian, boolean lactoOvoVegetarian, boolean lactoVegetarian, boolean vegan) {
		this.avatar = avatar;
		this.alias = alias;
		this.description = description;
		this.pescatarian = pescatarian;
		this.lactoOvoVegetarian = lactoOvoVegetarian;
		this.lactoVegetarian = lactoVegetarian;
		this.vegan = vegan;
	}

	public IngredientType (Object avatar2, Object alias2, Object description2, Object object, Object object2, Object object3, Object object4) {
		// TODO Auto-generated constructor stub
	}


	public Document getAvatar () {
		return avatar;
	}


	public void setAvatar (Document avatar) {
		this.avatar = avatar;
	}


	public String getAlias () {
		return alias;
	}


	public void setAlias (String alias) {
		this.alias = alias;
	}


	public boolean isPescatarian () {
		return pescatarian;
	}


	public void setPescatarian (boolean pescatarian) {
		this.pescatarian = pescatarian;
	}


	public boolean isLactoOvoVegetarian () {
		return lactoOvoVegetarian;
	}


	public void setLactoOvoVegetarian (boolean lactoOvoVegetarian) {
		this.lactoOvoVegetarian = lactoOvoVegetarian;
	}


	public boolean isLactoVegetarian () {
		return lactoVegetarian;
	}


	public void setLactoVegetarian (boolean lactoVegetarian) {
		this.lactoVegetarian = lactoVegetarian;
	}


	public boolean isVegan () {
		return vegan;
	}


	public void setVegan (boolean vegan) {
		System.out.println(" iamsetting....iamsetting....iamsetting...." + vegan);
		this.vegan = vegan;
	}


	public String getDescription () {
		return description;
	}


	public void setDescription (String description) {
		this.description = description;
	}
	
	

}
