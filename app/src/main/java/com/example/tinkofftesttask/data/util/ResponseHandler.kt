package com.example.tinkofftesttask.data.util

import android.content.Context
import com.bumptech.glide.load.HttpException
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    NoInternetConnection(-2)
}

@Singleton
class ResponseHandler @Inject constructor(@ApplicationContext private val context: Context) {

    fun <T> handleSuccess(data: T): Resource<T> {
        return Resource.Success(data)
    }

    fun <T> handleError(e: Exception, data: T?): Resource<T> {
        e.printStackTrace()
        return when (e) {
            is HttpException -> Resource.Error(getErrorMessage(e.statusCode), data)
            is IOException -> Resource.Error(
                getErrorMessage(ErrorCodes.NoInternetConnection.code),
                data
            )
            is SocketTimeoutException -> Resource.Error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                data
            )
            else -> Resource.Error(getErrorMessage(Int.MIN_VALUE), data)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Connection timeout"
            ErrorCodes.NoInternetConnection.code -> "No internet connection"
            401 -> "Необходима авторизация"
            403 -> "Доступ запрещен"
            404 -> "Не найдено"
            else -> "Неизвестная ошибка"
        }
    }

}