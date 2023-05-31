package edu.damago.cookbook.app;

import java.io.IOException;


/**
 * Facade for the recipe application.
 */
public class RecipeApp {

	/**
	 * Prevents external instantiation.
	 */
	private RecipeApp () {}


	/**
	 * Application entry point.
	 * @param args the runtime arguments
	 * @throws IOException if there is an I/O related problem reading from the shell
	 */
	static public void main (final String[] args) throws IOException {
		final RecipeController controller = new RecipeController();
		controller.rootView().display();
	}
}