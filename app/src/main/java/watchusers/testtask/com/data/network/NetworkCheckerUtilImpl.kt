package watchusers.testtask.com.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.CancellableDisposable

class NetworkCheckerUtilImpl(
    val context: Context,
    val scheduler: Scheduler = AndroidSchedulers.mainThread()
) : NetworkCheckerUtil {

    override fun checkNetwork()
            : Flowable<Network> {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return Flowable.create<Network>({ emitter ->

            val worker = scheduler.createWorker()
            val emit = { network: Network ->
                worker.schedule { emitter.onNext(network) }
            }

            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

            if (activeNetwork == null || !activeNetwork.isConnected)  emit(Network.NONE)

            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: android.net.Network) {
                    emit(Network.AVALIABLE)
                }

                override fun onUnavailable() {
                    emit(Network.NONE)
                }

                override fun onLost(network: android.net.Network) {
                    emit(Network.NONE)
                }
            }

            connectivityManager.requestNetwork(request, networkCallback)
            emitter.setDisposable(CompositeDisposable(
                worker,
                CancellableDisposable {
                    connectivityManager.unregisterNetworkCallback(networkCallback)
                }
            ))
        }, BackpressureStrategy.LATEST)
    }
}

enum class Network {
    NONE,
    AVALIABLE
}