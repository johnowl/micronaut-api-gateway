## How to mutate the request

1. create a filter that return a new request
2. repeat the filter
3. do what you want with the new request

This is possible because this

```java
return (Publisher<MutableHttpResponse<?>>) httpFilter.doFilter(requestReference.getAndSet(request), this);
```

To be more specific, this part:

```java
requestReference.getAndSet(request)
```

If the request should be immutable, why are they saving the request?

To turn the request mutable, this would work:

```java
requestReference.set(request)
return (Publisher<MutableHttpResponse<?>>) httpFilter.doFilter(requestReference.get(request), this);
```

I opened this PR https://github.com/micronaut-projects/micronaut-core/pull/5487/files before knowing that requests 
are immutable, only the attributes are mutable.
