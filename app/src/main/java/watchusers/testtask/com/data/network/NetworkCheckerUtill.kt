package watchusers.testtask.com.data.network

import io.reactivex.Flowable

interface NetworkCheckerUtil {
    fun checkNetwork(): Flowable<Network>
}