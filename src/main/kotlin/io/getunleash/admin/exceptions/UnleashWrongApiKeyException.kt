package io.getunleash.admin.exceptions

class UnleashWrongApiKeyException(override val message: String? = null, override val cause: Throwable? = null): Error(message, cause) {
}
