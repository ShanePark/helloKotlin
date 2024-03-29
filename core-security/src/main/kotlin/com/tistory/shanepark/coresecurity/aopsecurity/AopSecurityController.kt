package com.tistory.shanepark.coresecurity.aopsecurity

import com.tistory.shanepark.coresecurity.domain.dto.AccountDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class AopSecurityController(
    private val aopMethodService: AopMethodService,
    private val aopPointcutService: AopPointcutService,
) {

    @GetMapping("/preAuthorize")
    @PreAuthorize("hasRole('ROLE_USER') and #account.username == principal.name")
    fun preAuthorize(
        account: AccountDto?,
        model: Model,
        principal: Principal?,
    ): String {
        model.addAttribute("method", "Success @PreAuthorize")
        return "aop/method"
    }

    @GetMapping("/methodSecured")
    fun methodSecured(
        model: Model,
    ): String {
        aopMethodService.methodSecured()
        model.addAttribute("method", "Success MethodSecured")
        return "aop/method"
    }

    @GetMapping("/pointcutSecured")
    fun pointcutSecured(
        model: Model,
    ): String {
        aopPointcutService.pointcutSecured()
        aopPointcutService.notSecured()
        model.addAttribute("pointcut", "pointcut Secured")
        return "aop/method"
    }


}
