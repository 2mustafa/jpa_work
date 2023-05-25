module edu.damago.jpa.work {
	requires transitive java.instrument;

	requires transitive java.sql;
	requires transitive javax.persistence;
	requires transitive eclipselink.minus.jpa;
	requires transitive java.validation;

	exports edu.damago.seminar.persistence;
	opens edu.damago.seminar.persistence;

	exports edu.damago.cookbook.persistence;
	opens edu.damago.cookbook.persistence;
}