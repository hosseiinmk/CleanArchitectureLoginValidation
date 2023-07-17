package com.hosseinmohammadkarimi.loginvalidation.domain.use_case

class ValidatePassword {

    fun execute(password: String): ValidateResult {
        if (password.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "رمز عبور وارد نشده"
            )
        }
        if (password.length < 6) {
            return ValidateResult(
                successful = false,
                errorMessage = "رمز عبور باید حداقل شامل 6 کاراکتر باشد"
            )
        }
        if (!password.matches(Regex("[a-zA-Z0-9!^$%@]*"))) {
            return ValidateResult(
                successful = false,
                errorMessage = "رمز عبور باید انگلیسی باشد"
            )
        }
        if (!Regex("[A-Z]").containsMatchIn(password)) {
            return ValidateResult(
                successful = false,
                errorMessage = "رمز عبور باید شامل حداقل یک حرف بزرگ باشد"
            )
        }
        if (!Regex("[!^$%@]").containsMatchIn(password)) {
            return ValidateResult(
                successful = false,
                errorMessage = "رمز عبور باید شامل حداقل یکی از (@ % $ ^ !) باشد"
            )
        }
        if (!Regex("[0-9]").containsMatchIn(password)) {
            return ValidateResult(
                successful = false,
                errorMessage = "رمز عبور باید شامل حداقل یک عدد باشد"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}