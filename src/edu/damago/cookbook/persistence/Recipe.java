package edu.damago.cookbook.persistence;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(schema = "cookbook", name = "Recipe")
@PrimaryKeyJoinColumn(name = "recipeIdentity")
@DiscriminatorValue("Recipe")
public class Recipe extends BaseEntity {
	//category ENUM ("MAIN_COURSE", "APPETIZER", "SNACK", "DESSERT", "BREAKFAST", "BUFFET", "BARBEQUE", "ADOLESCENT", "INFANT") NOT NULL,
	static public enum Category {MAIN_COURSE, APPETIZER, SNACK, DESSERT, BREAKFAST, BUFFET, BARBEQUE, ADOLESCENT, INFANT}

	//avatarReference BIGINT NOT NULL,
	//FOREIGN KEY (avatarReference) REFERENCES Document (documentIdentity) ON DELETE RESTRICT ON UPDATE CASCADE,
	@ManyToOne(optional = false)
	@JoinColumn(name = "avatarReference", nullable = false, updatable = true)
	private Document avatar;

	//ownerReference BIGINT NULL,
	//FOREIGN KEY (ownerReference) REFERENCES Person (personIdentity) ON DELETE CASCADE ON UPDATE CASCADE,
	@ManyToOne(optional = true)
	@JoinColumn(name = "ownerReference", nullable = true, updatable = true)
	private Person owner;

	@NotNull
	@ManyToMany
	@JoinTable(
		schema = "cookbook",
		name = "RecipeIllustrationAssociation",
		joinColumns = @JoinColumn(name = "recipeReference", nullable = false, updatable = false, insertable = true),
		inverseJoinColumns = @JoinColumn(name = "documentReference", nullable = false, updatable = false, insertable = true),
		uniqueConstraints = @UniqueConstraint(columnNames = { "recipeReference", "documentReference" })
	)
	private Set<Document> illustrations;

	@NotNull
	@OneToMany(mappedBy = "recipe", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	private Set<Ingredient> ingredients;

	//title CHAR(128) NOT NULL,
	//UNIQUE KEY (title)
	@NotNull @Size(max = 128)
	@Column(nullable = false, updatable = true, length = 128, unique = true)
	private String title;
	
	//* -category: Recipe.Category recipeIdentity BIGINT NOT NULL, PRIMARY KEY (recipeIdentity), FOREIGN KEY (recipeIdentity)
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = true)
	private Category category;

	//description VARCHAR(4094) NULL,
	@Size(max = 40960)
	@Column(nullable = true, updatable = true)
	private String description;

	//instruction VARCHAR(4094) NULL,
	@Size(max = 4096)
	@Column(nullable = true, updatable = true)
	private String instruction;



	/**
	 * REFERENCES BaseEntity (identity) ON DELETE CASCADE ON UPDATE CASCADE,
	 **/

	public Recipe () {
		this.category = Category.MAIN_COURSE;
		this.ingredients = Collections.emptySet();
		this.illustrations = new HashSet<>();
	}



	public Document getAvatar () {
		return avatar;
	}



	public void setAvatar (Document avatar) {
		this.avatar = avatar;
	}



	public Person getOwner () {
		return owner;
	}



	public void setOwner (Person owner) {
		this.owner = owner;
	}



	public Set<Document> getIllustrations () {
		return illustrations;
	}



	protected void setIllustrations (Set<Document> illustrations) {
		this.illustrations = illustrations;
	}



	public Set<Ingredient> getIngredients () {
		return ingredients;
	}



	protected void setIngredients (Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}



	public String getTitle () {
		return title;
	}



	public void setTitle (String title) {
		this.title = title;
	}



	public Category getCategory () {
		return category;
	}



	public void setCategory (Category category) {
		this.category = category;
	}



	public String getDescription () {
		return description;
	}



	public void setDescription (String description) {
		this.description = description;
	}



	public String getInstruction () {
		return instruction;
	}



	public void setInstruction (String instruction) {
		this.instruction = instruction;
	}
	
	public boolean isPescatarian () {
		return this.ingredients.stream().allMatch(Ingredient::isPescatarian);			
	}


	public boolean isLactoOvoVegetarian () {
		return this.ingredients.stream().allMatch(Ingredient::isLactoOvoVegetarian);			
	}


	public boolean isLactoVegetarian () {
		return this.ingredients.stream().allMatch(Ingredient::isLactoVegetarian);			
	}


	public boolean isVegan () {
		return this.ingredients.stream().allMatch(Ingredient::isVegan);			
	}
}
