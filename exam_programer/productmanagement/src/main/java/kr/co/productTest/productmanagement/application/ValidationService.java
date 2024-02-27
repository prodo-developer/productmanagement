package kr.co.productTest.productmanagement.application;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated // 유효성 검사
public class ValidationService {
    public <T> void checkValid(@Valid T validationTarget) {

    }
}
