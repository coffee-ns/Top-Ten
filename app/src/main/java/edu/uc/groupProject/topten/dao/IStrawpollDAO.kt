package edu.uc.groupProject.topten.dao

import edu.uc.groupProject.topten.dto.Strawpoll
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IStrawpollDAO {
    @GET("polls/{id}")
    fun getStrawpoll(@Path("id") id: Int): Call<Strawpoll>
}