package edu.damago.cookbook.persistence;

public class Ingredient {
	IngredientType ingredientType;

	

	public String getDescription () {
		return this.ingredientType == null ? null : this.getDescription();
	}


	public String getAlias () {
		return this.ingredientType.getAlias();
	}


	public boolean isPescatarian () {
		return ingredientType.isPescatarian();
	}


	public boolean isLactoOvoVegetarian () {

		return this.ingredientType == null ? null : this.isLactoOvoVegetarian();
	}


	public boolean isLactoVegetarian () {
		return ingredientType.isLactoVegetarian();
	}


	public boolean isVegan () {
		return ingredientType.isVegan();
	}

}
