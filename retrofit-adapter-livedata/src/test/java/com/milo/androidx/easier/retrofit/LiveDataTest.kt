package com.milo.androidx.easier.retrofit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.CountDownLatch


class LiveDataTest {

    private val lifecycleOwner = Owner()
    private lateinit var user: LiveData<User?>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun observeLiveDataTest() {
        val latch = CountDownLatch(1)

        user.observe(lifecycleOwner, Observer { user ->
            assert(user != null)
            latch.countDown()
        })

        lifecycleOwner.mockOnResume()
        latch.await()
    }

    @Before
    fun before() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://example.com")
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()

        val delegate = MockRetrofit.Builder(retrofit)
            .networkBehavior(NetworkBehavior.create())
            .build()
            .create(Service::class.java)

        user = Transformations.map(
            delegate.returningResponse(
                User(
                    "milo",
                    "meng"
                )
            ).getData()
        ) { resp ->
            resp?.body
        }
    }

}