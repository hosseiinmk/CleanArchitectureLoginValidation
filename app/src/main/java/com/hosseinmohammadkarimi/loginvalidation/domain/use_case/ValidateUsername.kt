package com.hosseinmohammadkarimi.loginvalidation.domain.use_case

class ValidateUsername {

    fun execute(username: String): ValidateResult {
        if (username.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "نام کاربری وارد نشده است"
            )
        }
        if (username[0].isDigit()) {
            return ValidateResult(
                successful = false,
                errorMessage = "نام کاربری نمیتواند با عدد شروع شود"
            )
        }
        if (username.length < 3) {
            return ValidateResult(
                successful = false,
                errorMessage = "نام کاربری باید حداقل شامل 3 کاراکتر باشد"
            )
        }
        if (!username.matches(Regex("[a-zA-Z0-9]*"))) {
            return ValidateResult(
                successful = false,
                errorMessage = "نام کاربری باید انگلیسی باشد"
            )
        }
        if (!Regex("[A-Z]").containsMatchIn(username)) {
            return ValidateResult(
                successful = false,
                errorMessage = "نام کاربری باید شامل حداقل یک حرف بزرگ باشد"
            )
        }
        if (!Regex("[0-9]").containsMatchIn(username)) {
            return ValidateResult(
                successful = false,
                errorMessage = "نام کاربری باید شامل حداقل یک عدد باشد"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}