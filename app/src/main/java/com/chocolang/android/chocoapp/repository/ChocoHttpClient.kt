package com.chocolang.android.chocoapp.repository

import com.chocolang.android.chocoapp.repository.result.GitRepositoryResult
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ChocoHttpClient {
    private fun initHttp(target: String): StringBuilder? {
        val url = URL(BASE_URL_API + target)
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = "GET"

        if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
            val streamReader = InputStreamReader(urlConnection.inputStream)
            val buffered = BufferedReader(streamReader)

            val content = StringBuilder()
            while (true) {
                val line = buffered.readLine() ?: break
                content.append(line)
            }
            buffered.close()
            urlConnection.disconnect()

            return content
        }
        return null
    }

    fun getRepositoryList(q: String, page: Int): GitRepositoryResult {
        val content = initHttp("${SEARCH}repositories?q=${q}&page=$page")
        return Gson().fromJson(content.toString(), GitRepositoryResult::class.java)
    }
}