package edu.damago.cookbook.app;

import java.io.IOException;


/**
 * Facade for the seminar application.
 */
public class SeminarApp {

	/**
	 * Prevents external instantiation.
	 */
	private SeminarApp () {}


	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 * @throws IOException if there is an I/O related problem reading from the shell
	 */
	static public void main (final String[] args) throws IOException {
		final SeminarController controller = new SeminarController();
		controller.rootView().display();
	}
}