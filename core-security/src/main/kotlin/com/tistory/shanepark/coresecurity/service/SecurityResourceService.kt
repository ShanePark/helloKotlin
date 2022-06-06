package com.tistory.shanepark.coresecurity.service

import com.tistory.shanepark.coresecurity.repository.AccessIpRepository
import com.tistory.shanepark.coresecurity.repository.ResourcesRepository
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityConfig
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.stream.Collectors

class SecurityResourceService(
    private val resourcesRepository: ResourcesRepository,
    private val accessIpRepository: AccessIpRepository,
) {

    fun getResourceList(): LinkedHashMap<RequestMatcher, List<ConfigAttribute>> {
        val result: LinkedHashMap<RequestMatcher, List<ConfigAttribute>> = LinkedHashMap()
        val resourceList = resourcesRepository.findAllResources()
        resourceList?.forEach { resource ->
            val configAttributeList: MutableList<ConfigAttribute> = ArrayList()
            resource?.roleSet?.forEach { role ->
                configAttributeList.add(SecurityConfig(role.roleName))
            }
            result[AntPathRequestMatcher(resource?.resourceName)] = configAttributeList
        }
        return result
    }

    fun getAccessIpList(): MutableList<String>? {
        return accessIpRepository.findAll().stream().map { accessIp -> accessIp.ipAddress }.collect(Collectors.toList())
    }
}
