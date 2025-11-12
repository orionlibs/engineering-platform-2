package com.dimi.core.api;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.data.ResourceNotFoundException;
import com.dimi.core.exception.AError;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> onValidationError(MethodArgumentNotValidException ex)
    {
        List<APIField> fields = ex.getBindingResult()
                        .getFieldErrors().stream()
                        .map(fe -> new APIField(
                                        fe.getField(),
                                        fe.getDefaultMessage(),
                                        fe.getRejectedValue()))
                        .toList();
        APIResponse response = new APIResponse();
        AError<List<APIField>> error = new AError<>();
        error.setErrorMessage("Validation failed for one or more fields");
        error.setError(fields);
        response.setError(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }


    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<APIResponse> onDuplicateRecordException(DuplicateRecordException ex)
    {
        APIResponse response = new APIResponse();
        AError error = new AError<>();
        error.setErrorMessage("Duplicate database record found: " + ex.getMessage());
        response.setError(error);
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> onResourceNotFoundException(ResourceNotFoundException ex)
    {
        APIResponse response = new APIResponse();
        AError error = new AError<>();
        error.setErrorMessage("Resource not found: " + ex.getMessage());
        response.setError(error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }


    @ExceptionHandler(IdempotencyConflictException.class)
    public ResponseEntity<APIResponse> handleIdempotencyConflict(IdempotencyConflictException e)
    {
        APIResponse response = new APIResponse();
        AError error = new AError<>();
        error.setErrorMessage("Idempotency conflict");
        response.setError(error);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(response);
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<APIResponse> handleAllCheckedExceptions(Throwable ex)
    {
        APIResponse response = new APIResponse();
        AError error = new AError<>();
        error.setErrorMessage("An unexpected error occurred");
        response.setError(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }
}
