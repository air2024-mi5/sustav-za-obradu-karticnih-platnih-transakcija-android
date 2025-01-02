package hr.foi.air.szokpt.core.network

interface RequestHandler<T> {
    fun sendRequest(responseListener: ResponseListener<T>)
}