package hr.foi.air.core.network

interface RequestHandler<T> {
    fun sendRequest(responseListener: ResponseListener<T>)
}