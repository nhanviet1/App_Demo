package com.example.newsapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.Datasource.RetrofitInstance
import com.example.newsapp.Model1.Body
import com.example.newsapp.Model1.XYZ
import com.example.newsapp.Model2.ABC
import com.example.newsapp.Model2.BodyModel
import com.example.newsapp.ResponseParser

class GetNews() {
    private val retrofitService = RetrofitInstance.getRetrofitInstance()

    suspend fun getNews(bodyModel: BodyModel): LiveData<ABC> {
        var result = MutableLiveData<ABC>()
        val data = retrofitService.getNews(bodyModel.country, bodyModel.category, bodyModel.apiKey)
        result.value = data.body()!!
        return result
    }

//    suspend fun getNewsX(bodyModel: BodyModel): LiveData<ABC> {
//        var result = MutableLiveData<ABC>()
//        val data = retrofitService.getNews(bodyModel.country, bodyModel.category, bodyModel.apiKey)
//        result.postValue(data.body())
//        return result
//    }

     suspend fun getNews2(bodyModel: BodyModel): LiveData<ABC> {
        var result = MutableLiveData<ABC>()
        val abc = retrofitService.getNewsTest(bodyModel.country, bodyModel.category, bodyModel.apiKey)
        val data = ResponseParser.parseObject(abc, ABC::class.java)
        result.value = (data as ABC?)!!
        return result
    }

    suspend fun getNewsX(body: Body): LiveData<XYZ> {
        var result = MutableLiveData<XYZ>()
        val data = retrofitService.getNews2(body.q, body.from, body.sortBy, body.apiKey)
        result.value = data.body()!!
        return result
    }

}