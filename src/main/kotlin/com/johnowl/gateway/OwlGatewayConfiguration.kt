package com.johnowl.gateway

import javax.inject.Singleton

@Singleton
class OwlGatewayConfiguration {
    val routes: List<OwlGatewayRoute> = listOf(
        OwlGatewayRoute("httpbin_get", "https://httpbin.org", "/get", "GET"),
    )
}

data class OwlGatewayRoute(
    val id: String,
    val service: String,
    val path: String,
    val method: String
)