package edu.damago.cookbook.app;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import edu.damago.cookbook.persistence.Recipe;
import edu.damago.tool.CommandShell;
import edu.damago.tool.JSON;


/**
 * Controller for the recipe application.
 */
public class RecipeController {
	private final EntityManagerFactory entityManagerFactory;
	private final CommandShell rootView;


	/**
	 * Initialize this instance.
	 */
	public RecipeController () {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("mysql-local");
		this.rootView = new CommandShell();
		this.rootView.setExceptionHandler(e -> e.printStackTrace());

		// register event listeners
		this.rootView.addEventListener("quit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("exit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("help", parameterization -> this.performHelpCommand(parameterization));
		this.rootView.addEventListener("query-recipes", parameterization -> this.performQueryRecipesCommand(parameterization));
		this.rootView.addEventListener("insert-recipe", parameterization -> this.performInsertRecipeCommand(parameterization));
		this.rootView.addEventListener("update-recipe", parameterization -> this.performUpdateRecipeCommand(parameterization));
		this.rootView.addEventListener("delete-recipe", parameterization -> this.performDeleteRecipeCommand(parameterization));
	}


	/**
	 * Returns the root view.
	 * @return the root view component
	 */
	public CommandShell rootView () {
		return this.rootView;
	}


	/**
	 * Performs the quit command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performQuitCommand (final String parameterization) throws NullPointerException {
		System.out.println("Bye Bye!");
		System.exit(0);
	}


	/**
	 * Performs the help command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performHelpCommand (final String parameterization) throws NullPointerException {
		System.out.println("Available commands:");
		System.out.println("- quit: terminates this program");
		System.out.println("- help: displays this help");
		System.out.println("- query-recipes: displays all persistent recipes");
		System.out.println("- insert-recipe <JSON>: inserts a new recipe");
		System.out.println("- update-recipe <JSON>: updates an existing recipe");
		System.out.println("- delete-recipe <ID>: removes an existing recipe");
	}


	/**
	 * Performs the query-recipes command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performQueryRecipesCommand (final String parameterization) throws NullPointerException {
		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// creates a JP-QL query addressing Java classes and their properties
			final TypedQuery<Long> query = entityManager.createQuery("select r.identity from Recipe as r", Long.class);

			final List<Long> recipeIdentities = query.getResultList();
			for (final Long recipeIdentity : recipeIdentities) {
				// looks for the recipe within the 2nd level cache, and if not there then within the database, if not there returns null!
				final Recipe recipe = entityManager.find(Recipe.class, recipeIdentity);
				if (recipe == null) continue;

				System.out.format("Recipe: identity=%d, category=%s, title=%s, pescatarian=%b, lactoOvoVegetarian=%b, lactoVegetarian=%b, vegan=%b, ingredient-count=%d%n", recipe.getIdentity(), recipe.getCategory(), recipe.getTitle(), recipe.isPescatarian(), recipe.isLactoOvoVegetarian(), recipe.isLactoVegetarian(), recipe.isVegan(), recipe.getIngredients().size());
			}
			System.out.println("ok.");

			entityManager.getTransaction().commit();
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the insert-recipe command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performInsertRecipeCommand (final String parameterization) throws NullPointerException {
		final Map<String,Object> parameters = JSON.parse(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			final Recipe recipe = new Recipe();
			if (parameters.containsKey("category")) recipe.setCategory(Recipe.Category.valueOf((String) parameters.get("category")));
			if (parameters.containsKey("title")) recipe.setTitle((String) parameters.get("title"));

			// sends one CREATE statement to the database
			entityManager.persist(recipe);

			entityManager.getTransaction().commit();
			System.out.format("ok, recipe #%d created.%n", recipe.getIdentity());
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the update-recipe command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performUpdateRecipeCommand (final String parameterization) throws NullPointerException {
		final Map<String,Object> parameters = JSON.parse(parameterization);

		if (!parameters.containsKey("identity")) throw new IllegalArgumentException("JSON must contain the seminar identity!");
		final long recipeIdentity = ((Double) parameters.get("identity")).longValue();

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the recipe within the 2nd level cache, and if not there then within the database, if not there returns null!
			final Recipe recipe = entityManager.find(Recipe.class, recipeIdentity);
			if (parameters.containsKey("category")) recipe.setCategory(Recipe.Category.valueOf((String) parameters.get("category")));
			if (parameters.containsKey("title")) recipe.setTitle((String) parameters.get("title"));

			// sends UPDATE statements to the database
			entityManager.flush();

			entityManager.getTransaction().commit();
			System.out.println("ok.");
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the delete-recipe command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performDeleteRecipeCommand (final String parameterization) throws NullPointerException {
		final long recipeIdentity = Long.parseLong(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the recipe within the 2nd level cache, and if not there then within the database, if not there returns null!
			final Recipe recipe = entityManager.find(Recipe.class, recipeIdentity);

			// sends one DELETE statement to the database
			if (recipe != null) entityManager.remove(recipe);

			entityManager.getTransaction().commit();
			System.out.println("ok.");
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}
}