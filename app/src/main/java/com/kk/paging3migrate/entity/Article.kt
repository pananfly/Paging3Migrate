package com.kk.paging3migrate.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author kuky.
 * @description
 */

data class Article(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val curPage: Int,
    val datas: MutableList<DataX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

@Entity(tableName = "article")
data class DataX(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var rId: Long,
    @ColumnInfo(name = "author") var author: String,
    @ColumnInfo(name = "chapter_id") var chapterId: Int,
    @ColumnInfo(name = "chapter_name") var chapterName: String,
    @ColumnInfo(name = "collect") var collect: Boolean,
    @ColumnInfo(name = "description") var desc: String,
    @ColumnInfo(name = "article_id") var id: Int,
    @ColumnInfo(name = "link") var link: String,
    @ColumnInfo(name = "nice_date") var niceDate: String,
    @ColumnInfo(name = "publish_time") var publishTime: Long,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "zan") var zan: Int
)

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey var articleId: Int,
    @ColumnInfo(name = "prev_key") var prevKey: Int?,
    @ColumnInfo(name = "next_key") var nextKey: Int?
)
