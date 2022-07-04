package com.rainy.http_rxjava.api

import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * 主要功能: Retrofit2 协程通用的网络请求配置
 * @Description:
 * @author : jhy
 * @date: 2021年08月07日 1:21 下午
 * @version: 1.0.0
 */
interface RxJavaService {
    @GET
    fun doGet(
        @HeaderMap headers: HashMap<String, String>,
        @Url url: String
    ): Single<ResponseBody>

    @FormUrlEncoded
    @POST("{path}")
    fun doPost(
        @Path(value = "path", encoded = true) path: String,
        @HeaderMap headers: HashMap<String, String>,
        @FieldMap params: HashMap<String, Any>
    ): Single<ResponseBody>

    @POST("{path}")
    fun doBody(
        @Path(value = "path", encoded = true) path: String,
        @HeaderMap headers: HashMap<String, String>,
        @Body body: RequestBody
    ): Single<ResponseBody>

    @DELETE
    fun doDelete(
        @Url path: String,
        @HeaderMap headers: HashMap<String, String>,
        @QueryMap maps: Map<String, Any>
    ): Single<ResponseBody>

    @PUT
    fun doPut(
        @Url path: String,
        @HeaderMap headers: HashMap<String, String>,
        @FieldMap maps: HashMap<String, Any>
    ): Single<ResponseBody>

    @Streaming
    @GET
    fun download(
        @Header("RANGE") start: String,
        @Url url: String
    ): Single<ResponseBody>

    @Multipart
    @POST
    fun upload(
        @Url url: String,
        @HeaderMap headers: HashMap<String, String>,
        @QueryMap maps: Map<String, @JvmSuppressWildcards Any>,
        @PartMap files: Map<String, @JvmSuppressWildcards RequestBody>
    ): Single<ResponseBody>
}