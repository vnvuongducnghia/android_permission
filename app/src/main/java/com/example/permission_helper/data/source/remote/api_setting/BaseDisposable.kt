package com.example.permission_helper.data.source.remote.api_setting

import android.content.Context
import android.util.Log
import com.androidnetworking.error.ANError
import com.example.permission_helper.data.source.remote.api_setting.response.BaseModel
import com.example.permission_helper.data.source.remote.api_setting.response.ErrorData
import com.example.permission_helper.util.NetworkUtils
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

typealias OnSuccess<T> = (result: T?) -> Unit
typealias OnComplete = () -> Unit
typealias OnError<ErrorData> = (error: ErrorData?) -> Unit
typealias OnRetry = () -> Unit
typealias OnOffline = () -> Unit

class BaseDisposable<T>(var context: Context) {

    /**
     * Init variable
     */
    private var onSuccess: OnSuccess<T>? = null
    private var onComplete: OnComplete? = null
    private var onError: OnError<ErrorData>? = null
    private var onRetry: OnRetry? = null
    private var onOffline: OnOffline? = null
    private var canShowError = true
    private var type = Type.NONE
        set(value) {
            field = value
            canShowError = when (field) {
                Type.SILENT -> false
                else -> true
            }
        }

    init {
        /*if (context != null && context is BaseActivity) {
            this.view = (context as BaseActivity).getView()
        }*/
    }

    /**
     * Top method
     */
    fun start(observable: Observable<T?>, type: Type): Disposable? {
        this.type = type
        return start(observable)
    }

    fun start(observable: Observable<T?>): Disposable? {
        return if (NetworkUtils.isNetworkConnected(context)) {
            startWithoutCheckNetwork(observable)
        } else {
            showNoInternet()
            null
        }
    }

    private fun startWithoutCheckNetwork(observable: Observable<T?>): Disposable? {
        return observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                onComplete?.invoke()
                handlerError(it)
            }
            .subscribe({
                if (it is BaseModel && !it.errorMessage.isNullOrEmpty()) {
                    onError?.invoke(null)
                } else {
                    onSuccess?.invoke(it)
                }
                onComplete?.invoke()
            }, {
                onComplete?.invoke()
                onFailed(it)
            })

    }

    private fun onFailed(exception: Throwable) {
        if (exception.message != null) {
            Log.e("Disposable", exception.message)
        }
    }

    /*Handler Error*/
    private val NO_INTERNET = "接続できませんでした。ネットワーク接続環境を確認して、再度お試しください。"
    private val ERROR_400 = "入力した内容を見直してください。"
    private val ERROR_404 = "Not Found: Các tài nguyên hiện tại không được tìm thấy "
    private val ERROR_409 = "Conflict: Request không thể được hoàn thành bởi vì sự xung đột, ví dụ như là xung đột giữa nhiều chỉnh sửa đồng thời."
    private val ERROR_500 = "Internal Server Error: Thông báo lỗi chung chung."
    private val ERROR_503 = "Service Unavailable: Trạng thái tạm thời, Server quá tải hoặc bảo trì."

    private fun handlerError(throwable: Any) {
        var isIllegalStateException = false
        if (throwable is JsonSyntaxException) {
            isIllegalStateException = throwable.cause is IllegalStateException
            Log.e("ErrorParseJson", throwable.message)
        }
        if (!isIllegalStateException) { //Time Out || DNS Error
            if (throwable is ANError) {
                Log.e("ErrorCode", throwable.errorCode.toString())
                Log.e("ErrorDetail", throwable.errorDetail)
                val message = getMessageError(throwable.errorCode)
                if (throwable.errorCode == 503) {
                    showErrorMaintenance(message)
                } else {
                    showErrorNormal(message)
                }
                onError?.invoke(
                    ErrorData(
                        throwable.errorDetail,
                        throwable.errorCode
                    )
                )
            } else {
                onError?.invoke(null)
            }
        } else {
            onError?.invoke(null)
        }
    }

    private fun getMessageError(errorCode: Int): String {
        return when (errorCode) {
            400 -> ERROR_400
            404 -> ERROR_404
            409 -> ERROR_409
            500 -> ERROR_500
            503 -> ERROR_503
            else -> ERROR_500
        }
    }

    private fun showErrorMaintenance(message: String) {
        onOffline?.invoke()
        onComplete?.invoke()
        if (canShowError) {
            // Show dialog
        }
    }

    private fun showErrorNormal(message: String) {
        onOffline?.invoke()
        onComplete?.invoke()
        if (canShowError) {
            // Show dialog
        }
    }

    private fun showNoInternet() {
        onOffline?.invoke()
        onError?.invoke(null)
        onComplete?.invoke()
        // Show dialog
    }

    /**
     * Builder
     */
    fun onSuccess(onSuccess: OnSuccess<T>): BaseDisposable<T> {
        this.onSuccess = onSuccess
        return this
    }

    fun onComplete(onComplete: OnComplete): BaseDisposable<T> {
        this.onComplete = onComplete
        return this
    }

    fun onError(onError: OnError<ErrorData>): BaseDisposable<T> {
        this.onError = onError
        return this
    }

    fun onRetry(onRetry: OnRetry): BaseDisposable<T> {
        this.onRetry = onRetry
        return this
    }

    fun onOffline(onOffline: OnOffline): BaseDisposable<T> {
        this.onOffline = onOffline
        return this
    }

    /**
     * Class
     */
    enum class Type {
        NONE, SILENT
    }

    /**
    Status code là một số nguyên 3 ký tự, ký tự đầu tiên định nghĩa loại Response và hai ký tự cuối không có bất cứ vai trò phân loại nào.
    Có 5 giá trị của ký tự đầu tiên:
    1xx: Information (Thông tin): Khi nhận được những mã như vậy tức là request đã được server tiếp nhận và quá trình xử lý request đang được tiếp tục.
    2xx: Success (Thành công): Khi nhận được những mã như vậy tức là request đã được server tiếp nhận, hiểu và xử lý thành công
    3xx: Redirection (Chuyển hướng): Mã trạng thái này cho biết client cần có thêm action để hoàn thành request
    4xx: Client Error (Lỗi Client): Nó nghĩa là request chứa cú pháp không chính xác hoặc không được thực hiện.
    5xx: Server Error (Lỗi Server): Nó nghĩa là Server thất bại với việc thực hiện một request nhìn như có vẻ khả thi.
    https://viblo.asia/p/tim-hieu-ve-http-status-code-lA7GKwx5GKZQ
     */
}