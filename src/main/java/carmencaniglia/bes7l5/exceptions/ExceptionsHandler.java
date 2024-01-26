package carmencaniglia.bes7l5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;


@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)//400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayload handleBadRequest(BadRequestException ex){
        return new ErrorsPayload(ex.getMessage(), LocalTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsPayload handleUnauthorized(UnauthorizedException e) {
        return new ErrorsPayload(e.getMessage(), LocalTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorsPayload handleAccessDenied(AccessDeniedException ex) {
        return new ErrorsPayload("Your role does not allow access to this functionality!!", LocalTime.now());
    }

    @ExceptionHandler(NotFoundException.class)//404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsPayload handleNotFound(NotFoundException ex){
        return new ErrorsPayload(ex.getMessage(), LocalTime.now());
    }

    @ExceptionHandler(Exception.class)//500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsPayload handleGeneric(Exception ex){
        ex.printStackTrace();
        return new ErrorsPayload("Internal server error!!!", LocalTime.now());
    }
}
