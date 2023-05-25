package edu.damago.cookbook.app;

import java.io.IOException;


/**
 * Facade for the document application.
 */
public class DocumentApp {

	/**
	 * Prevents external instantiation.
	 */
	private DocumentApp () {}


	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 * @throws IOException if there is an I/O related problem reading from the shell
	 */
	static public void main (final String[] args) throws IOException {
		final DocumentController controller = new DocumentController();
		controller.rootView().display();
	}
}