package edu.damago.cookbook.app;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import edu.damago.cookbook.persistence.Person;
import edu.damago.tool.CommandShell;
import edu.damago.tool.JSON;


/**
 * Controller for the person application.
 */
public class PersonController {
	private final EntityManagerFactory entityManagerFactory;
	private final CommandShell rootView;


	/**
	 * Initialize this instance.
	 */
	public PersonController () {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("mysql-local");
		this.rootView = new CommandShell();
		this.rootView.setExceptionHandler(e -> e.printStackTrace());

		// register event listeners
		this.rootView.addEventListener("quit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("exit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("help", parameterization -> this.performHelpCommand(parameterization));
		this.rootView.addEventListener("query-people", parameterization -> this.performQueryPeopleCommand(parameterization));
		this.rootView.addEventListener("1", parameterization -> this.performQueryPeopleCommand(parameterization));
		this.rootView.addEventListener("insert-person", parameterization -> this.performInsertPersonCommand(parameterization));
		this.rootView.addEventListener("update-person", parameterization -> this.performUpdatePersonCommand(parameterization));
		this.rootView.addEventListener("delete-person", parameterization -> this.performDeletePersonCommand(parameterization));
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
		System.out.println("- query-people: displays all persistent people");
		System.out.println("- insert-person <JSON>: inserts a new person");
		System.out.println("- update-person <JSON>: updates an existing person");
		System.out.println("- delete-person <ID>: removes an existing person");
	}


	/**
	 * Performs the query-people command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performQueryPeopleCommand (final String parameterization) throws NullPointerException {
		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// creates a JP-QL query addressing Java classes and their properties
			final TypedQuery<Long> query = entityManager.createQuery("select p.identity from Person as p", Long.class);

			final List<Long> personIdentities = query.getResultList();
			for (final Long personIdentity : personIdentities) {
				// looks for the person within the 2nd level cache, and if not there then within the database, if not there returns null!
				final Person person = entityManager.find(Person.class, personIdentity);
				if (person == null) continue;

				System.out.format("Person: identity=%d, email=%s, passwordHash=%s, group=%s, name=%s, address=%s%n", person.getIdentity(), person.getEmail(), person.getPasswordHash(), person.getGroup(), person.getName(), person.getAddress());
			}
			System.out.println("ok.");

			entityManager.getTransaction().commit();
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the insert-person command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performInsertPersonCommand (final String parameterization) throws NullPointerException {
		final Map<String,Object> parameters = JSON.parse(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			final Person person = new Person();
			if (parameters.containsKey("email")) person.setEmail((String) parameters.get("email"));
			if (parameters.containsKey("passwordHash")) person.setPasswordHash((String) parameters.get("passwordHash"));
			if (parameters.containsKey("group")) person.setGroup(Person.Group.valueOf((String) parameters.get("group")));
			if (parameters.containsKey("name")) {
				@SuppressWarnings("unchecked")
				final Map<String,Object> name = (Map<String,Object>) parameters.get("name");
				if (name.containsKey("title")) person.getName().setTitle((String) name.get("title"));
				if (name.containsKey("family")) person.getName().setFamily((String) name.get("family"));
				if (name.containsKey("given")) person.getName().setGiven((String) name.get("given"));
			}
			if (parameters.containsKey("address")) {
				@SuppressWarnings("unchecked")
				final Map<String,Object> address = (Map<String,Object>) parameters.get("address");
				if (address.containsKey("street")) person.getAddress().setStreet((String) address.get("street"));
				if (address.containsKey("city")) person.getAddress().setCity((String) address.get("city"));
				if (address.containsKey("country")) person.getAddress().setCountry((String) address.get("country"));
				if (address.containsKey("postcode")) person.getAddress().setPostcode((String) address.get("postcode"));
			}

			// sends one CREATE statement to the database
			entityManager.persist(person);

			entityManager.getTransaction().commit();
			System.out.format("ok, person #%d created.%n", person.getIdentity());
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the update-person command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performUpdatePersonCommand (final String parameterization) throws NullPointerException {
		final Map<String,Object> parameters = JSON.parse(parameterization);

		if (!parameters.containsKey("identity")) throw new IllegalArgumentException("JSON must contain the seminar identity!");
		final long personIdentity = ((Double) parameters.get("identity")).longValue();

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the person within the 2nd level cache, and if not there then within the database, if not there returns null!
			final Person person = entityManager.find(Person.class, personIdentity);
			if (parameters.containsKey("email")) person.setEmail((String) parameters.get("email"));
			if (parameters.containsKey("passwordHash")) person.setPasswordHash((String) parameters.get("passwordHash"));
			if (parameters.containsKey("group")) person.setGroup(Person.Group.valueOf((String) parameters.get("group")));
			if (parameters.containsKey("name")) {
				@SuppressWarnings("unchecked")
				final Map<String,Object> name = (Map<String,Object>) parameters.get("name");
				if (name.containsKey("title")) person.getName().setTitle((String) name.get("title"));
				if (name.containsKey("family")) person.getName().setFamily((String) name.get("family"));
				if (name.containsKey("given")) person.getName().setGiven((String) name.get("given"));
			}
			if (parameters.containsKey("address")) {
				@SuppressWarnings("unchecked")
				final Map<String,Object> address = (Map<String,Object>) parameters.get("address");
				if (address.containsKey("street")) person.getAddress().setStreet((String) address.get("street"));
				if (address.containsKey("city")) person.getAddress().setCity((String) address.get("city"));
				if (address.containsKey("country")) person.getAddress().setCountry((String) address.get("country"));
				if (address.containsKey("postcode")) person.getAddress().setPostcode((String) address.get("postcode"));
			}
			if (parameters.containsKey("phones")) {
				@SuppressWarnings("unchecked")
				final List<String> phones = (List<String>) parameters.get("phones");
				person.getPhones().retainAll(phones);
				person.getPhones().addAll(phones);
			}

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
	 * Performs the delete-person command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performDeletePersonCommand (final String parameterization) throws NullPointerException {
		final long personIdentity = Long.parseLong(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the person within the 2nd level cache, and if not there then within the database, if not there returns null!
			final Person person = entityManager.find(Person.class, personIdentity);

			// sends one DELETE statement to the database
			if (person != null) entityManager.remove(person);

			entityManager.getTransaction().commit();
			System.out.println("ok.");
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}
}