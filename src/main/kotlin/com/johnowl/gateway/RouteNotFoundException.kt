package com.johnowl.gateway

class RouteNotFoundException(path: String, method: String) : Throwable("Route $method $path not found")