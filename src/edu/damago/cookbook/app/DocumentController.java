package edu.damago.cookbook.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import edu.damago.cookbook.persistence.Document;
import edu.damago.tool.CommandShell;


/**
 * Controller for the document application.
 */
public class DocumentController {
	private final EntityManagerFactory entityManagerFactory;
	private final CommandShell rootView;


	/**
	 * Initialize this instance.
	 */
	public DocumentController () {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("mysql-local");
		this.rootView = new CommandShell();
		// this.rootView.setExceptionHandler(e -> e.printStackTrace());

		// register event listeners
		this.rootView.addEventListener("quit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("exit", parameterization -> this.performQuitCommand(parameterization));
		this.rootView.addEventListener("help", parameterization -> this.performHelpCommand(parameterization));
		this.rootView.addEventListener("query-documents", parameterization -> this.performQueryDocumentsCommand(parameterization));
		this.rootView.addEventListener("insert-document", parameterization -> this.performInsertDocumentCommand(parameterization));
		this.rootView.addEventListener("delete-document", parameterization -> this.performDeleteDocumentCommand(parameterization));
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
		System.out.println("- query-documents: displays all persistent documents");
		System.out.println("- insert-document <file-path>: inserts a new document");
		System.out.println("- delete-document <ID>: removes an existing document");
	}


	/**
	 * Performs the query-documents command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 */
	public void performQueryDocumentsCommand (final String parameterization) throws NullPointerException {
		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// creates a JP-QL query addressing Java classes and their properties
			final TypedQuery<Long> query = entityManager.createQuery("select d.identity from Document as d", Long.class);

			final List<Long> documentIdentities = query.getResultList();
			for (final Long documentIdentity : documentIdentities) {
				// looks for the seminar within the 2nd level cache, and if not there then within the database, if not there returns null!
				final Document document = entityManager.find(Document.class, documentIdentity);
				if (document == null) continue;

				System.out.format("Document: identity=%d, type=%s, hash=%s, content-length=%d%n", document.getIdentity(), document.getType(), document.getHash(), document.getContent().length);
			}
			System.out.println("ok.");

			entityManager.getTransaction().commit();
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the insert-document command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 * @throws IOException if there is an I/O related problem
	 */
	public void performInsertDocumentCommand (final String parameterization) throws NullPointerException, IOException {
		final Path filePath = Paths.get(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			final byte[] content = Files.readAllBytes(filePath);
			final String type = Files.probeContentType(filePath);
			final Document document = new Document(type, content);

			// sends one INSERT statement to the database
			entityManager.persist(document);

			entityManager.getTransaction().commit();
			System.out.format("ok, document #%d created.%n", document.getIdentity());
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}


	/**
	 * Performs the delete-document command.
	 * @param parameterization the command parameterization, empty for none
	 * @throws NullPointerException if the given parameterization is null
	 * @throws ParseException if there is a problem with date parsing
	 */
	public void performDeleteDocumentCommand (final String parameterization) throws NullPointerException, ParseException {
		final long documentIdentity = Long.parseLong(parameterization);

		final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			// looks for the seminar within the 2nd level cache, and if not there then within the database, if not there returns null!
			final Document document = entityManager.find(Document.class, documentIdentity);

			// sends one DELETE statement to the database
			if (document != null) entityManager.remove(document);

			entityManager.getTransaction().commit();
			System.out.println("ok.");
		} finally {
			if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}
}