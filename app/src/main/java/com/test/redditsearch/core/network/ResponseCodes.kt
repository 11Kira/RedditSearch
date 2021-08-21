package com.test.redditsearch.core.network

enum class ResponseCodes(val code: Int) {
    SOCKET_TIMEOUT(-1),
    NOT_FOUND(404),
    UNAUTHORIZED(401),
    INTERNAL_SERVER_ERROR(500),
    FORBIDDEN(403),
    BAD_REQUEST(400),
    NOT_IMPLEMENTED(501),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504),
    UNKNOWN_ERROR(0),
    OK(200)
}