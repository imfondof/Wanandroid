package com.imfondof.wanandroid.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit

/**
 * @Description:
 * @CreateDate: 2022/1/19 9:39
 */
class HttpLogInterceptor : Interceptor {
    private val TAG = "shuo_HttpLogInterceptor"
    private val UTF8 = Charset.forName("UTF-8")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headers = request.headers()
        val requestBody = request.body()
        var body: String? = null
        requestBody?.let {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset: Charset? = UTF8
            val contentType = requestBody.contentType()
            contentType?.let {
                charset = contentType.charset(UTF8)
            }
            body = buffer.readString(charset!!)
        }

        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        Log.d(TAG, request.method() + "：" + request.url() + "[time:" + tookMs + "ms][body is $body][heads is ${headers.toString().replace("\n", "||")}")

        val responseBody = response.body()
        val rBody: String

        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()

        var charset: Charset? = UTF8
        val contentType = responseBody.contentType()
        contentType?.let {
            try {
                charset = contentType.charset(UTF8)
            } catch (e: UnsupportedCharsetException) {
                e.message?.let { it1 -> Log.e(TAG, it1) }
            }
        }
        rBody = buffer.clone().readString(charset!!)

        Log.d(TAG, "RES：" + response.request().url() + rBody)
        return response
    }
}