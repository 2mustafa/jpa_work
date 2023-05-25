package edu.damago.cookbook.app;

import java.io.IOException;


/**
 * Facade for the document application.
 */
public class IngredientTypeApp {

	/**
	 * Prevents external instantiation.
	 */
	private IngredientTypeApp () {}


	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 * @throws IOException if there is an I/O related problem reading from the shell
	 */
	static public void main (final String[] args) throws IOException {
		final IngredientTypeController controller = new IngredientTypeController();
		controller.rootView().display();
	}
}