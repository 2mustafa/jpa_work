package edu.damago.seminar.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import edu.damago.seminar.persistence.Seminar;
import edu.damago.tool.CommandShell;
import edu.damago.tool.JSON;


/**
 * Controller for the seminar application.
 */
public class SeminarController {
	private final EntityManagerFactory entityManagerFactory;
	private final SimpleDateFormat dateFormatter;
	private final CommandShell rootView;


	/**
	 * Initialize this instance.
	 */
	public SeminarController () {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("mysql-local");
		this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		this.rootView = new CommandShell();
		// this.rootView.setExceptionHandler(e -> e.printStackTrace());

		// register event listeners
		this.rootView.addEventListener("quit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("exit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("help", parameterization -> this.performHelpCommand(parameterization));
		this.rootView.addEventListener("query-seminars", parameterization -> this.performQuerySeminarsCommand(parameterization));
		this.rootView.addEventListener("insert-seminar", parameterization -> this.performInsertSeminarCommand(parameterization));
		this.rootView.addEventListener("update-seminar", parameterization -> this.performUpdateSeminarCommand(parameterization));
		this.rootView.addEventListener("delete-seminar", parameterization -> this.performDeleteSeminarCommand(parameterization));
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
		System.out.println("- query-seminars: displays all persistent seminars");
		System.out.println("- insert-seminar <JSON>: inserts a new seminar");
		System.out.println("- update-seminar <JSON>: updates an existing seminar");
		System.out.println("- delete-seminar <ID>: removes an existing seminar");
	}


	/**
	 * Performs the query-seminars command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performQuerySeminarsCommand (final String parameterization) throws NullPointerException {
		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// creates a JP-QL query addressing Java classes and their properties
			final TypedQuery<Integer> query = entityManager.createQuery("select s.identity from Seminar as s", Integer.class);

			final List<Integer> seminarIdentities = query.getResultList();
			for (final Integer seminarIdentity : seminarIdentities) {
				// looks for the seminar within the 2nd level cache, and if not there then within the database, if not there returns null!
				final Seminar seminar = entityManager.find(Seminar.class, seminarIdentity);
				if (seminar == null) continue;

				System.out.format("Seminar: identity=%d, day=%tF, location=%s, topic=%s, description=%s%n", seminar.getIdentity(), seminar.getDay(), seminar.getLocation(), seminar.getTopic(), seminar.getDescription());
			}
			System.out.println("ok.");

			entityManager.getTransaction().commit();
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the insert-seminar command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 * @throws ParseException if there is a problem with date parsing
	 */
	public void performInsertSeminarCommand (final String parameterization) throws NullPointerException, ParseException {
		final Map<String,Object> parameters = JSON.parse(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			final Seminar seminar = new Seminar();
			seminar.setDay(this.dateFormatter.parse((String) parameters.get("day")));
			seminar.setLocation((String) parameters.get("location"));
			seminar.setTopic((String) parameters.get("topic"));
			seminar.setDescription((String) parameters.get("description"));

			// sends one INSERT statement to the database
			entityManager.persist(seminar);

			entityManager.getTransaction().commit();
			System.out.format("ok, seminar #%d created.%n", seminar.getIdentity());
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the update-seminar command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 * @throws ParseException if there is a problem with date parsing
	 */
	public void performUpdateSeminarCommand (final String parameterization) throws NullPointerException, ParseException {
		final Map<String,Object> parameters = JSON.parse(parameterization);

		if (!parameters.containsKey("identity")) throw new IllegalArgumentException("JSON must contain the seminar identity!");
		final int seminarIdentity = ((Double) parameters.get("identity")).intValue();

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the seminar within the 2nd level cache, and if not there then within the database, if not there returns null!
			final Seminar seminar = entityManager.find(Seminar.class, seminarIdentity);
			if (seminar == null) throw new IllegalArgumentException("seminar does not exist!");

			if (parameters.containsKey("day")) seminar.setDay(this.dateFormatter.parse((String) parameters.get("day")));
			if (parameters.containsKey("location")) seminar.setLocation((String) parameters.get("location"));
			if (parameters.containsKey("topic")) seminar.setTopic((String) parameters.get("topic"));
			if (parameters.containsKey("description")) seminar.setDescription((String) parameters.get("description"));

			// sends the required UPDATE statements to the database
			entityManager.flush();

			entityManager.getTransaction().commit();
			System.out.println("ok.");
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the delete-seminar command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 * @throws ParseException if there is a problem with date parsing
	 */
	public void performDeleteSeminarCommand (final String parameterization) throws NullPointerException, ParseException {
		final int seminarIdentity = Integer.parseInt(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the seminar within the 2nd level cache, and if not there then within the database, if not there returns null!
			final Seminar seminar = entityManager.find(Seminar.class, seminarIdentity);

			// sends one DELETE statement to the database
			if (seminar != null) entityManager.remove(seminar);

			entityManager.getTransaction().commit();
			System.out.println("ok.");
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}
}