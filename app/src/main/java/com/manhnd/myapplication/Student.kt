package com.manhnd.myapplication

import com.google.gson.annotations.SerializedName

data class Student(
    val id: Int,
    val mssv: String,
    @SerializedName("hoten")
    val hoten: String,
    val ngaysinh: String,
    val email: String,
    val thumbnail: String,
) {
    fun thumbnailUrl(): String {
        return if (thumbnail.startsWith("http")) {
            thumbnail
        } else {
            "https://lebavui.io.vn$thumbnail"
        }
    }
}
