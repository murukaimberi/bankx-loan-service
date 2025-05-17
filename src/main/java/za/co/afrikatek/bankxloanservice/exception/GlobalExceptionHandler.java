/**
 * Author : murukaigumbo-mberi
 * Date   : 2025/05/17
 * Time   : 16:30
 */

package za.co.afrikatek.bankxloanservice.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        Map<String, List<String>> errorMap = Map.of("errors", errors);
        String objectName = ex.getBindingResult().getObjectName();
        ErrorResponse errorResponse = new ErrorResponse("Validation errors", HttpStatus.BAD_REQUEST, objectName,errorMap);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Status400BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Status400BadRequestException ex) {
        String message = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.BAD_REQUEST, ex.getEntity(), ex.getErrors());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
