package com.chocolang.android.chocoapp.repository

private const val DOMAIN_URL = "https://api.github.com/"
val BASE_URL_API = getBaseApiUrl()

const val SEARCH = "search/"
const val SAMPLE = "users/"

private fun getBaseApiUrl(): String {
    val baseUrl = DOMAIN_URL
    return baseUrl
}