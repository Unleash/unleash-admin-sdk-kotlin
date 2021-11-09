package io.getunleash.admin.exceptions

class UnleashUniqueViolationException(override val message: String? = null, override val cause: Throwable? = null) : Error(message, cause)
