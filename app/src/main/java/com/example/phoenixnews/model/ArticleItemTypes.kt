package com.example.phoenixnews.model

sealed class ArticleItemTypes {



    data class HeaderModel (val author :String?) : ArticleItemTypes ()
    data class ArticleModel (val urlToImage: String?,
                             var title: String?,
                             val source: Source?,
                             val description: String?,
                             val publishedAt: String?,
                             var id:Int?=null,
    ) : ArticleItemTypes ()



}
