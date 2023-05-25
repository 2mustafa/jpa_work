package edu.damago.tool;

/**
 * Facade for additional String operations.
 */
@Copyright(year = 2017, holders = "Sascha Baumeister")
public class Strings {
	static public enum Alignment { LEFT, CENTER, RIGHT }


	/**
	 * Prevents external instantiation.
	 */
	private Strings () {}


	/**
	 * Returns a copy of the given text, resized to the given size. 
	 * @param text the text, or {@code null} for empty
	 * @param size the size
	 * @param alignment the alignment
	 * @param pad the padding character
	 * @return the text created
	 * @throws NullPointerException if the given alignment is null
	 * @throws IllegalArgumentException if the given size is strictly negative
	 */
	static public String resize (final String text, final int size, final Alignment alignment, final char pad) throws NullPointerException, IllegalArgumentException {
		if (size < 0) throw new IllegalArgumentException(String.valueOf(size));
		final StringBuilder factory = new StringBuilder(text == null ? "" : text);

		while (factory.length() < size) {
			switch (alignment) {
				case LEFT:
					factory.append(pad);
					break;
				case RIGHT:
					factory.insert(0, pad);
					break;
				default:
					throw new IllegalArgumentException();
			}
		}

		if (factory.length() > size) {
			switch (alignment) {
				case LEFT:
					factory.delete(size, factory.length());
					break;
				case RIGHT:
					factory.delete(0, factory.length() - size);
					break;
				default:
					throw new IllegalArgumentException();
			}
		}

		return factory.toString();
	}


	/**
	 * Returns how often the given character occurs within the given text. 
	 * @param text the text
	 * @param character the character
	 * @return the number of character occurrences
	 * @throws NullPointerException if the given text is {@code null}
	 */
	static public int occurrences (final String text, final char character) throws NullPointerException {
		int occurrences = 0;

		for (int index = 0; index < text.length(); ++index)
			if (text.charAt(index) == character) occurrences += 1;

		return occurrences;
	}


	/**
	 * Returns how often the given code-point occurs within the given text. 
	 * @param text the text
	 * @param codePoint the code-point
	 * @return the number of code-point occurrences
	 * @throws NullPointerException if the given text is {@code null}
	 */
	static public int occurrences (final String text, final int codePoint) throws NullPointerException {
		int occurrences = 0;

		for (int index = 0; index < text.length(); ++index)
			if (text.codePointAt(index) == codePoint) occurrences += 1;

		return occurrences;
	}
}