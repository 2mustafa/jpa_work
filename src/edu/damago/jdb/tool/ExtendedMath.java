package edu.damago.jdb.tool;


/**
 * Facade for extended math operations.
 */
@Copyright(year = 2021, holders = "Sascha Baumeister")
public class ExtendedMath {

	/**
	 * Prevents external instantiation.
	 */
	private ExtendedMath () {}


	/**
	 * Returns the minimum of the given values.
	 * @param values the values as a var-arg array
	 * @return the minimum value
	 * @throws IllegalArgumentException if the given values are empty
	 */
	static public int min (final int... values) throws IllegalArgumentException {
		if (values.length == 0) throw new IllegalArgumentException();

		int minimum = values[0];
		for (int index = 1; index < values.length; ++index) {
			final int value = values[index];
			if (value < minimum) minimum = value;
		}

		return minimum;
	}


	/**
	 * Returns the minimum of the given values.
	 * @param values the values as a var-arg array
	 * @return the minimum value
	 * @throws IllegalArgumentException if the given values are empty
	 */
	static public long min (final long... values) throws IllegalArgumentException {
		if (values.length == 0) throw new IllegalArgumentException();

		long minimum = values[0];
		for (int index = 1; index < values.length; ++index) {
			final long value = values[index];
			if (value < minimum) minimum = value;
		}

		return minimum;
	}


	/**
	 * Returns the minimum of the given values.
	 * @param values the values as a var-arg array
	 * @return the minimum value
	 * @throws IllegalArgumentException if the given values are empty
	 */
	static public float min (final float... values) throws IllegalArgumentException {
		if (values.length == 0) throw new IllegalArgumentException();

		float minimum = values[0];
		for (int index = 1; index < values.length; ++index) {
			final float value = values[index];
			if (value < minimum) minimum = value;
		}

		return minimum;
	}


	/**
	 * Returns the minimum of the given values.
	 * @param values the values as a var-arg array
	 * @return the minimum value
	 * @throws IllegalArgumentException if the given values are empty
	 */
	static public double min (final double... values) throws IllegalArgumentException {
		if (values.length == 0) throw new IllegalArgumentException();

		double minimum = values[0];
		for (int index = 1; index < values.length; ++index) {
			final double value = values[index];
			if (value < minimum) minimum = value;
		}

		return minimum;
	}


	/**
	 * Returns the maximum of the given values.
	 * @param values the values as a var-arg array
	 * @return the maximum value
	 * @throws IllegalArgumentException if the given values are empty
	 */
	static public int max (final int... values) throws IllegalArgumentException {
		if (values.length == 0) throw new IllegalArgumentException();

		int maximum = values[0];
		for (int index = 1; index < values.length; ++index) {
			final int value = values[index];
			if (value > maximum) maximum = value;
		}

		return maximum;
	}


	/**
	 * Returns the maximum of the given values.
	 * @param values the values as a var-arg array
	 * @return the maximum value
	 * @throws IllegalArgumentException if the given values are empty
	 */
	static public long max (final long... values) throws IllegalArgumentException {
		if (values.length == 0) throw new IllegalArgumentException();

		long maximum = values[0];
		for (int index = 1; index < values.length; ++index) {
			final long value = values[index];
			if (value > maximum) maximum = value;
		}

		return maximum;
	}


	/**
	 * Returns the maximum of the given values.
	 * @param values the values as a var-arg array
	 * @return the maximum value
	 * @throws IllegalArgumentException if the given values are empty
	 */
	static public float max (final float... values) throws IllegalArgumentException {
		if (values.length == 0) throw new IllegalArgumentException();

		float maximum = values[0];
		for (int index = 1; index < values.length; ++index) {
			final float value = values[index];
			if (value > maximum) maximum = value;
		}

		return maximum;
	}


	/**
	 * Returns the maximum of the given values.
	 * @param values the values as a var-arg array
	 * @return the maximum value
	 * @throws IllegalArgumentException if the given values are empty
	 */
	static public double max (final double... values) throws IllegalArgumentException {
		if (values.length == 0) throw new IllegalArgumentException();

		double maximum = values[0];
		for (int index = 1; index < values.length; ++index) {
			final double value = values[index];
			if (value > maximum) maximum = value;
		}

		return maximum;
	}


	/**
	 * Rounds the given value to the given number of decimal digits.
	 * @param value the value
	 * @param digitCount the number of decimal digits 
	 * @return the rounded value
	 * @throws IllegalArgumentException if the given digit count is strictly negative
	 */
	static public float round (final float value, final int digitCount) throws IllegalArgumentException {
		if (digitCount < 0) throw new IllegalArgumentException();

		final float norm = (float) Math.pow(10, digitCount);
		return Math.round(value * norm) / norm;
	}


	/**
	 * Rounds the given value to the given number of decimal digits.
	 * @param value the value
	 * @param digitCount the number of decimal digits 
	 * @return the rounded value
	 * @throws IllegalArgumentException if the given digit count is strictly negative
	 */
	static public double round (final double value, final int digitCount) throws IllegalArgumentException {
		if (digitCount < 0) throw new IllegalArgumentException();

		final double norm = Math.pow(10, digitCount);
		return Math.round(value * norm) / norm;
	}


	/**
	 * Rounds the given value down to the given number of decimal digits.
	 * @param value the value
	 * @param digitCount the number of decimal digits 
	 * @return the floored value
	 * @throws IllegalArgumentException if the given digit count is strictly negative
	 */
	static public float floor (final float value, final int digitCount) throws IllegalArgumentException {
		if (digitCount < 0) throw new IllegalArgumentException();

		final float norm = (float) Math.pow(10, digitCount);
		return (float) Math.floor(value * norm) / norm;
	}


	/**
	 * Rounds the given value down to the given number of decimal digits.
	 * @param value the value
	 * @param digitCount the number of decimal digits 
	 * @return the floored value
	 * @throws IllegalArgumentException if the given digit count is strictly negative
	 */
	static public double floor (final double value, final int digitCount) throws IllegalArgumentException {
		if (digitCount < 0) throw new IllegalArgumentException();

		final double norm = Math.pow(10, digitCount);
		return Math.floor(value * norm) / norm;
	}


	/**
	 * Rounds the given value up to the given number of decimal digits.
	 * @param value the value
	 * @param digitCount the number of decimal digits 
	 * @return the ceilinged value
	 * @throws IllegalArgumentException if the given digit count is strictly negative
	 */
	static public float ceil (final float value, final int digitCount) throws IllegalArgumentException {
		if (digitCount < 0) throw new IllegalArgumentException();

		final float norm = (float) Math.pow(10, digitCount);
		return (float) Math.ceil(value * norm) / norm;
	}


	/**
	 * Rounds the given value up to the given number of decimal digits.
	 * @param value the value
	 * @param digitCount the number of decimal digits 
	 * @return the ceilinged value
	 * @throws IllegalArgumentException if the given digit count is strictly negative
	 */
	static public double ceil (final double value, final int digitCount) throws IllegalArgumentException {
		if (digitCount < 0) throw new IllegalArgumentException();

		final double norm = Math.pow(10, digitCount);
		return Math.ceil(value * norm) / norm;
	}


	/**
	 * Returns the root of the given value to the given base.
	 * @param value the value
	 * @param base the base
	 * @return the resulting root
	 */
	static public double root (final double value, final double base) {
		return Math.pow(value, 1.0 / base);
	}


	/**
	 * Returns the logarithm of the given value to the given base.
	 * @param value the value
	 * @param base the base
	 * @return the resulting logarithm
	 */
	static public double log (final double value, final double base) {
		return Math.log(value) / Math.log(base);
	}
}