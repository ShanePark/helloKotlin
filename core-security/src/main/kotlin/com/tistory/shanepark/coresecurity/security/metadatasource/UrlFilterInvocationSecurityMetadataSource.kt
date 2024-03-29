package com.tistory.shanepark.coresecurity.security.metadatasource

import com.tistory.shanepark.coresecurity.service.SecurityResourceService
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.function.Consumer

class UrlFilterInvocationSecurityMetadataSource(
    private val requestMap: LinkedHashMap<RequestMatcher, List<ConfigAttribute>>,
    private val securityResourceService: SecurityResourceService,
) : FilterInvocationSecurityMetadataSource {

    override fun getAttributes(`object`: Any?): List<ConfigAttribute>? {

        val request = (`object` as FilterInvocation).request
        if (requestMap != null) {
            for (entry in requestMap.entries) {
                val matcher = entry.key
                if (matcher.matches(request)) {
                    return entry.value
                }
            }
        }
        return null
    }

    override fun getAllConfigAttributes(): MutableCollection<ConfigAttribute> {
        val allAttributes: MutableSet<ConfigAttribute> = HashSet()
        requestMap.values.forEach(Consumer<Collection<ConfigAttribute>> { c: Collection<ConfigAttribute>? ->
            allAttributes.addAll(
                c!!)
        })
        return allAttributes
    }

    override fun supports(clazz: Class<*>?): Boolean {
        return FilterInvocation::class.java.isAssignableFrom(clazz)
    }

    fun reload() {
        requestMap.clear()
        for (entry in securityResourceService.getResourceList().entries) {
            requestMap[entry.key] = entry.value
        }

    }
}
