package com.rhnlf.firstsubmission.api

import com.rhnlf.firstsubmission.BuildConfig
import com.rhnlf.firstsubmission.data.DetailUser
import com.rhnlf.firstsubmission.data.GithubResponse
import com.rhnlf.firstsubmission.data.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getGithub(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailUser>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username: String?
    ): Call<List<User>>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String?
    ): Call<List<User>>
}