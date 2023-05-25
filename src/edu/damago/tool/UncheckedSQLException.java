package edu.damago.tool;

import java.sql.SQLException;
import java.util.Objects;


/**
 * Wraps an {@link SQLException} with an unchecked exception.
 */
@SuppressWarnings("serial")
@Copyright(year = 2021, holders = "Sascha Baumeister")
public class UncheckedSQLException extends RuntimeException {

	/**
	 * Constructs an instance of this class.
	 * @param message the detail message, can be null
	 * @param cause the {@code SQLException}
	 * @throws NullPointerException if the cause is {@code null}
	 */
	public UncheckedSQLException (final String message, final SQLException cause) throws NullPointerException {
		// constructors are never inherited, but always chained!
		super(message, Objects.requireNonNull(cause));
	}


	/**
	 * Constructs an instance of this class.
	 * @param cause the {@code SQLException}
	 * @throws NullPointerException if the cause is {@code null}
	 */
	public UncheckedSQLException (final SQLException cause) throws NullPointerException {
		// constructors are never inherited, but always chained!
		this(cause.getMessage(), cause);
	}


	/**
	 * Returns the cause of this exception.
	 * @return the {@code SQLException} which is the cause of this exception
	 */
	@Override
	public SQLException getCause () {
		return (SQLException) super.getCause();
	}
}