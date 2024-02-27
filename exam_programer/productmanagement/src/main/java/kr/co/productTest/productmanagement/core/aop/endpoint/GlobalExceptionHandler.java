package kr.co.productTest.productmanagement.core.aop.endpoint;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import kr.co.productTest.productmanagement.core.aop.endpoint.exception.EntityValidationException;
import kr.co.productTest.productmanagement.core.feature.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorMessage> handConstraintViolatedException(ConstraintViolationException ex) {
//        // 예외에 대한 처리
//        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
//        List<String> errors = constraintViolations.stream()
//                .map(constraintViolation -> constraintViolation.getPropertyPath() + ", " +
//                        constraintViolation.getMessage())
//                .collect(Collectors.toList());
//        // List<String>을 하나의 String으로 변환
//        ErrorMessage errorMessage = new ErrorMessage(errors);
//        // 이 경우, 컴파일러는 ResponseEntity의 제네릭 타입을 Object로 처리합니다.
//        //따라서 두 코드 사이에는 기능적인 차이는 없지만, <>를 사용한 코드가 타입 안정성 측면에서 약간 더 좋을 수 있습니다. 제네릭을 사용함으로써 컴파일 시점에 타입 오류를 잡아낼 수 있기 때문입니다.
////        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handConstraintViolatedException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errors = constraintViolations.stream()
                .map(constraintViolation ->
                        extractField(constraintViolation.getPropertyPath()) + ", " + constraintViolation.getMessage()
                )
                .collect(Collectors.toList());
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ", " + fieldError.getDefaultMessage())
                .toList();

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<ErrorMessage> handleEntityValidationException(EntityValidationException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    private String extractField(Path path) {
        String[] splittedArray = path.toString().split("[.]");
        int lastIndex = splittedArray.length - 1;
        return splittedArray[lastIndex];
    }
}
