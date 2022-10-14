package com.example.newsapp.Viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.newsapp.Model1.Article2
import com.example.newsapp.Model1.Body
import com.example.newsapp.Model2.Article
import com.example.newsapp.Model2.BodyModel
import com.example.newsapp.Repository.GetNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewmodel() : ViewModel() {
    private val getNewsRepository = GetNews()
    var netWorkStatus = MutableLiveData<String>("Loading")
    private var _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> get() = _news
    private var _newsX = MutableLiveData<List<Article>>()
    val newsX: LiveData<List<Article>> get() = _news
    private var _news2 = MediatorLiveData<List<Article>>()
    val news2: LiveData<List<Article>> get() = _news2

    private var _news3 = MediatorLiveData<List<Article2>>()
    val news3: LiveData<List<Article2>> get() = _news3

    init {
        _news2.addSource(news) {
            _news2.value = it
        }
        getNews()
        abc()
//        getNewsTest()
    }

    fun getNews() {
        viewModelScope.launch {
            try {
                val a = getNewsRepository.getNews(BodyModel())
                _news.value = a.value!!.articles
//                _news.postValue()
                netWorkStatus.value = "Success"
            } catch (e: Exception) {
                _news.value = listOf()
            }
        }
    }

    fun abc() {
        viewModelScope.launch {
            val b = getNewsRepository.getNews2(BodyModel())
            Log.d("nhan", "${b.value!!.articles.size}")
        }
    }

    fun getNews2() {
        viewModelScope.launch {
            try {
                val a = getNewsRepository.getNewsX(Body())
                _news3.value = a.value!!.articles
//                _news.postValue()
                netWorkStatus.value = "Success"
            } catch (e: Exception) {
                _news.value = listOf()
            }
        }
    }

//    fun getNewsTest() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val a = getNewsRepository.getNewsX(BodyModel())
//            _newsX.postValue(a.value!!.articles)
//        }
//    }

}