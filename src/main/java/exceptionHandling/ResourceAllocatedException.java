package exceptionHandling;

/**
 * <h1>ResourceAllocatedException</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
public class ResourceAllocatedException extends Exception {

	 private static final long serialVersionUID = -470180507998010368L;

	public ResourceAllocatedException() {
		super();
	}

	public ResourceAllocatedException(final String message) {
		super(message);
	}
}
