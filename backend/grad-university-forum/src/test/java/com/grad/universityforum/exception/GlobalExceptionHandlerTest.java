package com.grad.universityforum.exception;

import com.grad.universityforum.model.Result;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

  @Test
  void testHandleBusinessException() {
    // 创建业务异常
    BusinessException exception = new BusinessException("业务异常");

    // 处理异常
    Result<?> result = exceptionHandler.handleBusinessException(exception);

    // 验证结果
    assertNotNull(result);
    assertEquals(500, result.getCode());
    assertEquals("业务异常", result.getMessage());
  }

  @Test
  void testHandleBusinessExceptionWithCode() {
    // 创建带错误码的业务异常
    BusinessException exception = new BusinessException(400, "参数错误");

    // 处理异常
    Result<?> result = exceptionHandler.handleBusinessException(exception);

    // 验证结果
    assertNotNull(result);
    assertEquals(400, result.getCode());
    assertEquals("参数错误", result.getMessage());
  }

  @Test
  void testHandleValidationException() {
    // 模拟数据绑定错误
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = mock(FieldError.class);

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
    when(fieldError.getField()).thenReturn("username");
    when(fieldError.getDefaultMessage()).thenReturn("用户名不能为空");

    // 处理异常
    ResponseEntity<Result<?>> responseEntity = exceptionHandler.handleValidationException(exception);

    // 验证结果
    assertNotNull(responseEntity);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    Result<?> result = responseEntity.getBody();
    assertNotNull(result);
    assertEquals(400, result.getCode());
    assertEquals("username: 用户名不能为空", result.getMessage());
  }

  @Test
  void testHandleResourceNotFoundException() {
    // 创建资源不存在异常
    ResourceNotFoundException exception = new ResourceNotFoundException("帖子不存在");

    // 处理异常
    ResponseEntity<Result<?>> responseEntity = exceptionHandler.handleResourceNotFoundException(exception);

    // 验证结果
    assertNotNull(responseEntity);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    Result<?> result = responseEntity.getBody();
    assertNotNull(result);
    assertEquals(404, result.getCode());
    assertEquals("帖子不存在", result.getMessage());
  }

  @Test
  void testHandleException() {
    // 创建普通异常
    Exception exception = new RuntimeException("系统错误");

    // 处理异常
    ResponseEntity<Result<?>> responseEntity = exceptionHandler.handleException(exception);

    // 验证结果
    assertNotNull(responseEntity);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    Result<?> result = responseEntity.getBody();
    assertNotNull(result);
    assertEquals(500, result.getCode());
    assertEquals("系统错误", result.getMessage());
  }
}