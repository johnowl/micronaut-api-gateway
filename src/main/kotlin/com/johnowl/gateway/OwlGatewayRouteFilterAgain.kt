package com.johnowl.gateway

import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.OncePerRequestHttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.micronaut.http.filter.ServerFilterPhase
import org.reactivestreams.Publisher

@Filter("/**")
class OwlGatewayRouteFilterAgain(
    private val config: OwlGatewayConfiguration
) : OncePerRequestHttpServerFilter() {

    override fun getOrder() = ServerFilterPhase.RENDERING.order()

    override fun doFilterOnce(request: HttpRequest<*>?, chain: ServerFilterChain?): Publisher<MutableHttpResponse<*>> {

        require(request != null) { "Argument [request] can't be null" }
        require(chain != null) { "Argument [chain] can't be null" }

        val route = config.routes.firstOrNull { it.path == request.path && it.method == request.methodName }
        route ?: return Publishers.just(RouteNotFoundException(request.path, request.methodName))

        val newRequest = HttpRequest.create<Any>(HttpMethod.valueOf(route.method), "${route.service}${request.path}")

        for(header in request.headers) {
            for(value in header.value) {
                newRequest.headers.set(header.key, value)
            }
        }

        return chain.proceed(newRequest)
    }
}