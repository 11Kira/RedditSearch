package com.test.redditsearch.core.response

import com.test.redditsearch.core.network.Resource
import com.test.redditsearch.core.network.ResponseCodes
import retrofit2.HttpException
import java.net.SocketTimeoutException

class ResponseHandler {

    /**
     * Handles a successful response.
     * @param T The generic type/class to output.
     * @return The [Resource.success] data.
     */
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    /**
     * Handles a response [Exception].
     * @param e The exception caught.
     * @param T The data type/class to output.
     * @return The [Resource.error] data.
     */
    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(ResponseCodes.SOCKET_TIMEOUT.code), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    /**
     * Retrieves a generic error message [String], based on the given status code.
     * @param code The HTTP error/response code.
     * @return The relevant error message.
     */
    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ResponseCodes.SOCKET_TIMEOUT.code -> ResponseCodes.SOCKET_TIMEOUT.name
            ResponseCodes.UNAUTHORIZED.code -> ResponseCodes.UNAUTHORIZED.name
            ResponseCodes.NOT_FOUND.code -> ResponseCodes.NOT_FOUND.name
            else -> ResponseCodes.UNKNOWN_ERROR.name
        }
    }
}