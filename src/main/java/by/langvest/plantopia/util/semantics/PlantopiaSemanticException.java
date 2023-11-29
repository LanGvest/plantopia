package by.langvest.plantopia.util.semantics;

import org.jetbrains.annotations.NotNull;

public class PlantopiaSemanticException extends RuntimeException {
	public PlantopiaSemanticException(String message) {
		super(message);
	}

	public static class UnableToSet extends RuntimeException {
		public UnableToSet(String propertyName, @NotNull PlantopiaSemanticType<?, ?> type) {
			super(String.format("Unable to set '%s' property to '%s' semantic type properties.", propertyName, type.getId()));
		}
	}

	public static class Required extends RuntimeException {
		public Required(String propertyName, @NotNull PlantopiaSemanticType<?, ?> type) {
			super(String.format("'%s' property is required for '%s' semantic type properties.", propertyName, type.getId()));
		}
	}
}