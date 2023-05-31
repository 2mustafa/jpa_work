package edu.damago.cookbook.app;

import java.io.IOException;


/**
 * Facade for the person application.
 */
public class PersonApp {

	/**
	 * Prevents external instantiation.
	 */
	private PersonApp () {}


	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 * @throws IOException if there is an I/O related problem reading from the shell
	 */
	static public void main (final String[] args) throws IOException {
		final PersonController controller = new PersonController();
		controller.rootView().display();
	}
}