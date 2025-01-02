package foi.air.szokpt.utils

interface Validator<T> {
    fun validate(data: T): ValidationResult
}