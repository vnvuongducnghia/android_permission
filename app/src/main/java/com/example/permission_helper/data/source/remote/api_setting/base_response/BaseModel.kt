package com.example.permission_helper.data.source.remote.api_setting.base_response


class BaseModel {
    var httpCode: Int? = 200
    var statusCode: Int? = 200
    var body: ErrorData? = null
    var errorMessage: String? = null
    var errorType: String? = null
}