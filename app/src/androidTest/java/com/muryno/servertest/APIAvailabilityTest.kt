package com.muryno.servertest

import com.muryno.BuildConfig
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.Charset

class APIAvailabilityTest {
    @Test
    @Throws(Exception::class)
    fun testNetworkFetchingData () {
        //testing the api request
        val connection =
            URL(BuildConfig.BASE_URL_TEST).openConnection()
        val response = connection.getInputStream()
        val buffer = StringBuffer()
        BufferedReader(
            InputStreamReader(
                response,
                Charset.defaultCharset()
            )
        ).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                buffer.append(line)
            }
        }
        assert(buffer.isNotEmpty())
    }
}