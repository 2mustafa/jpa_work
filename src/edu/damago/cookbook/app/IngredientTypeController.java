package edu.damago.cookbook.app;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import edu.damago.cookbook.persistence.Document;
import edu.damago.cookbook.persistence.IngredientType;
import edu.damago.tool.CommandShell;
import edu.damago.tool.JSON;


/**
 * Controller for the document application.
 */
public class IngredientTypeController {
	private final EntityManagerFactory entityManagerFactory;
	private final CommandShell rootView;


	/**
	 * Initialize this instance.
	 */
	public IngredientTypeController () {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("mysql-local");
		this.rootView = new CommandShell();
		this.rootView.setExceptionHandler(e -> e.printStackTrace());

		// register event listeners
		this.rootView.addEventListener("quit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("exit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("help", parameterization -> this.performHelpCommand(parameterization));
		this.rootView.addEventListener("query-ingredient-types", parameterization -> this.performQueryIngredientTypesCommand(parameterization));
		this.rootView.addEventListener("insert-ingredient-type", parameterization -> this.performInsertIngredientTypeCommand(parameterization));
		this.rootView.addEventListener("update-ingredient-type", parameterization -> this.performUpdateIngredientTypeCommand(parameterization));
		this.rootView.addEventListener("delete-ingredient-type", parameterization -> this.performDeleteIngredientTypeCommand(parameterization));
		
		this.rootView.addEventListener("1", parameterization -> this.performQueryIngredientTypesCommand(parameterization));
		this.rootView.addEventListener("2", parameterization -> this.performUpdateIngredientTypeCommand(parameterization));

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
		System.out.println("- query-ingredient-types: displays all persistent ingredient types");
		System.out.println("- insert-ingredient-type <JSON>: inserts a new ingredient type");
		System.out.println("- update-ingredient-type <JSON>: updates an existing ingredient type");
		System.out.println("- delete-ingredient-type <ID>: removes an existing ingredient type");
	}


	/**
	 * Performs the query-ingredient-types command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performQueryIngredientTypesCommand (final String parameterization) throws NullPointerException {
		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// creates a JP-QL query addressing Java classes and their properties
			final TypedQuery<Long> query = entityManager.createQuery("select t.identity from IngredientType as t", Long.class);

			final List<Long> typeIdentities = query.getResultList();
			for (final Long typeIdentity : typeIdentities) {
				// looks for the ingredient type within the 2nd level cache, and if not there then within the database, if not there returns null!
				final IngredientType type = entityManager.find(IngredientType.class, typeIdentity);
				if (type == null) continue;

				System.out.format("IngredientType: identity=%d, alias=%s, pescatarian=%b, lactoOvoVegetarian=%b, lactoVegetarian=%b, vegan=%b, description=%s%n", type.getIdentity(), type.getAlias(), type.isPescatarian(), type.isLactoOvoVegetarian(), type.isLactoVegetarian(), type.isVegan(), type.getDescription());
			}
			System.out.println("ok.");

			entityManager.getTransaction().commit();
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the insert-ingredient-type command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 * @throws IOException if there is an I/O related problem
	 */
	public void performInsertIngredientTypeCommand (final String parameterization) throws NullPointerException, IOException {
		final Map<String,Object> parameters = JSON.parse(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			final IngredientType type = new IngredientType();

			if (parameters.containsKey("alias")) type.setAlias((String) parameters.get("alias"));
			if (parameters.containsKey("pescatarian")) type.setPescatarian((Boolean) parameters.get("pescatarian"));
			if (parameters.containsKey("lactoOvoVegetarian")) type.setLactoOvoVegetarian((Boolean) parameters.get("lactoOvoVegetarian"));
			if (parameters.containsKey("lactoVegetarian")) type.setLactoVegetarian((Boolean) parameters.get("lactoVegetarian"));
			if (parameters.containsKey("vegan")) type.setVegan((Boolean) parameters.get("vegan"));
			if (parameters.containsKey("description")) type.setDescription((String) parameters.get("description"));

			final Document defaultAvatar = entityManager.find(Document.class, 1L);
			if (defaultAvatar == null) throw new IllegalArgumentException("default avatar does not exist!");
			type.setAvatar(defaultAvatar);

			// sends one CREATE statement to the database
			entityManager.persist(type);

			entityManager.getTransaction().commit();
			System.out.format("ok, ingredient type #%d created.%n", type.getIdentity());
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the update-ingredient-type command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 * @throws IOException if there is an I/O related problem
	 */
	public void performUpdateIngredientTypeCommand (final String parameterization) throws NullPointerException, IOException {
		final Map<String,Object> parameters = JSON.parse(parameterization);

		if (!parameters.containsKey("identity")) throw new IllegalArgumentException("JSON must contain the seminar identity!");
		final long typeIdentity = ((Double) parameters.get("identity")).longValue();

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the ingredient type within the 2nd level cache, and if not there then within the database, if not there returns null!
			final IngredientType type = entityManager.find(IngredientType.class, typeIdentity);
			if (parameters.containsKey("alias")) type.setAlias((String) parameters.get("alias"));
			if (parameters.containsKey("pescatarian")) type.setPescatarian((Boolean) parameters.get("pescatarian"));
			if (parameters.containsKey("lactoOvoVegetarian")) type.setLactoOvoVegetarian((Boolean) parameters.get("lactoOvoVegetarian"));
			if (parameters.containsKey("lactoVegetarian")) type.setLactoVegetarian((Boolean) parameters.get("lactoVegetarian"));
			if (parameters.containsKey("vegan")) type.setVegan((Boolean) parameters.get("vegan"));
			if (parameters.containsKey("description")) type.setDescription((String) parameters.get("description"));

			final Document defaultAvatar = entityManager.find(Document.class, 1L);
			if (defaultAvatar == null) throw new IllegalArgumentException("default avatar does not exist!");
			type.setAvatar(defaultAvatar);

			// sends one CREATE statement to the database
			entityManager.persist(type);

			entityManager.getTransaction().commit();
			System.out.println("ok.");
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the delete-ingredient-type command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 * @throws ParseException if there is a problem with date parsing
	 */
	public void performDeleteIngredientTypeCommand (final String parameterization) throws NullPointerException, ParseException {
		final long typeIdentity = Long.parseLong(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the ingredient type within the 2nd level cache, and if not there then within the database, if not there returns null!
			final IngredientType type = entityManager.find(IngredientType.class, typeIdentity);

			// sends one DELETE statement to the database
			if (type != null) entityManager.remove(type);

			entityManager.getTransaction().commit();
			System.out.println("ok.");
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}
}