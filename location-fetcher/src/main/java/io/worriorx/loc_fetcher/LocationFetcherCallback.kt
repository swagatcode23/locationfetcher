package io.worriorx.loc_fetcher

interface LocationFetcherCallback {
    fun onLocationFetched(latitude: Double?, longitude: Double?)
}