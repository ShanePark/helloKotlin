package com.tistory.shanepark.coresecurity.security.common

import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class FormAuthenticationDetailsSource : AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    override fun buildDetails(context: HttpServletRequest): WebAuthenticationDetails {
        return FormWebAuthenticationDetails(context)
    }
}
