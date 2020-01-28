package com.rubber.admin.core.plugins.result;

import cn.hutool.luffyu.util.result.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-01-28 17:43
 **/

@Slf4j
@ControllerAdvice
public class ResponseResultMsgHandler implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否需要拦截
     * 返回true会执行 beforeBodyWrite
     * 返回false不会执行
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    /**
     * 拦截全局的数据信息
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (! (body instanceof ResultMsg)){
            body = ResultMsg.success(body);
        }
        return body;
    }
}
