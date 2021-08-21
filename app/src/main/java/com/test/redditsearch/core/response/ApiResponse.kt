package com.test.redditsearch.core.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data model for generic API responses.
 * @author Julius Villagracia
 */
data class ApiResponse(
    @SerializedName("kind")
    val kind: String? = "",
    @SerializedName("data")
    val data: ApiData
): Serializable