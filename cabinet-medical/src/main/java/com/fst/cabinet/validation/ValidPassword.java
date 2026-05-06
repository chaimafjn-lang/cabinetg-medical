
package com.fst.cabinet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { PasswordValidator.class })  // ← accolades {} obligatoires
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "8 car. min · 1 majuscule · 1 minuscule · 1 chiffre · 1 spécial (@$!%*?&)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}