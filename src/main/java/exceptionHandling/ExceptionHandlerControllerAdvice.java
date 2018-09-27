package exceptionHandling;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>ExceptionHandlerControllerAdvice</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ResourceAllocatedException.class)
	public String handleResourceAllocated(final ResourceAllocatedException exception,
			final HttpServletRequest request) {
		request.setAttribute("errorName", exception.getMessage());
		request.setAttribute("mode", "MODE_ERROR");
		return "welcomepage";
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFound(final ResourceNotFoundException exception, final HttpServletRequest request) {
		request.setAttribute("errorName", exception.getMessage());
		request.setAttribute("mode", "MODE_ERROR");
		request.setAttribute("userset", "YES");
		return "welcomepage";
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleException(final Exception exception, final HttpServletRequest request) {
		request.setAttribute("errorName", exception.getMessage());
		request.setAttribute("mode", "MODE_ERROR");
		return "welcomepage";

	}

}
