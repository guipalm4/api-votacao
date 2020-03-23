package com.guiPalma.apivotacao.endpoint.service.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PautaInsertValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface VotoInsert {
	
	String message() default "Erro de validação";

	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

}
