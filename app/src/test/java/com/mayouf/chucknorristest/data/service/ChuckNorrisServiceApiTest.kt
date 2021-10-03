package com.mayouf.chucknorristest.data.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mayouf.chucknorristest.data.source.remote.ChuckNorrisServiceApi
import com.mayouf.chucknorristest.domain.model.ChuckNorrisModel
import com.mayouf.chucknorristest.utils.Constants
import com.mayouf.chucknorristest.utils.createOkHttpBuilderExtension
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ChuckNorrisServiceApiTest {

    private lateinit var api: ChuckNorrisServiceApi
    private var mockWebServer: MockWebServer = MockWebServer()
    private val builder = OkHttpClient.Builder()
    private lateinit var gson: Gson
    private lateinit var body: String

    @Before
    fun setUp() {
        api = mock()
        mockWebServer.start()
        gson = GsonBuilder()
            .setLenient()
            .create()
        body = "{\n" +
                "    \"type\": \"success\",\n" +
                "    \"value\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"joke\": \"Chuck Norris uses ribbed condoms inside out, so he gets the pleasure.\",\n" +
                "            \"categories\": [\n" +
                "                \"explicit\"\n" +
                "            ]\n" +
                "        },{\n" +
                "            \"id\": 618,\n" +
                "            \"joke\": \"Chuck Norris puts sunglasses on to protect the sun from his eyes.\",\n" +
                "            \"categories\": []\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 619,\n" +
                "            \"joke\": \"Chuck Norris can lock a safe and keep the key inside it.\",\n" +
                "            \"categories\": []\n" +
                "        }\n" +
                "    ]\n" +
                "}"

    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test that get jokes succeeds with no error`() {

        val okHttpClient: OkHttpClient = builder.createOkHttpBuilderExtension(
            body, 200
        )

        val retrofit = Retrofit.Builder().baseUrl(mockWebServer.url(Constants.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ChuckNorrisServiceApi::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("content-type", "application/json")
        )

        val service: ChuckNorrisServiceApi = retrofit as ChuckNorrisServiceApi

        val call: TestObserver<ChuckNorrisModel> = service.getChuckNorrisJokes().test()

        call.awaitTerminalEvent()
        call.assertNoErrors()
        call.assertComplete()
    }

    @Test
    fun `test that get jokes succeeds with error`() {

        val okHttpClient: OkHttpClient = builder.createOkHttpBuilderExtension(
            body, 500
        )

        val retrofit = Retrofit.Builder().baseUrl(mockWebServer.url(Constants.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ChuckNorrisServiceApi::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setHeader("content-type", "application/json")
        )

        val service: ChuckNorrisServiceApi = retrofit as ChuckNorrisServiceApi

        val call: TestObserver<ChuckNorrisModel> = service.getChuckNorrisJokes().test()

        call.awaitTerminalEvent()
        call.assertErrorMessage("HTTP 500 OK")
    }
}