package io.getunleash.admin.exceptions

class UnleashIllegalContentTypeException(override val message: String? = null, override val cause: Throwable? = null, val contentTypeHeader: String): Error(message, cause) {
}
