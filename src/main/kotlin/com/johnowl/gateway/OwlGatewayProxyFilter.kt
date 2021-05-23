package com.johnowl.gateway

import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.client.RxProxyHttpClient
import io.micronaut.http.filter.OncePerRequestHttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.micronaut.http.filter.ServerFilterPhase
import org.reactivestreams.Publisher

@Filter("/**")
class OwlGatewayProxyFilter(
    private val proxyHttpClient: RxProxyHttpClient
) : OncePerRequestHttpServerFilter() {

    override fun getOrder() = ServerFilterPhase.LAST.order()

    override fun doFilterOnce(request: HttpRequest<*>?, chain: ServerFilterChain?): Publisher<MutableHttpResponse<*>> {

        require(request != null) { "Argument [request] can't be null" }
        require(chain != null) { "Argument [chain] can't be null" }

        return proxyHttpClient.proxy(request)
    }
}