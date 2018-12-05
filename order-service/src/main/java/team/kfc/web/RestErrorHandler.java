package team.kfc.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 */
@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(value=IllegalArgumentException.class)
	public ResponseEntity<RestError> handleInvalidArgument(final HttpServletRequest request, final IllegalArgumentException e) {
		return new ResponseEntity<>(new RestError(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
