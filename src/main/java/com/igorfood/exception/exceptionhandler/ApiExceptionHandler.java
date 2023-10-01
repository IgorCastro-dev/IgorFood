package com.igorfood.exception.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.igorfood.exception.EntidadeEmUsoException;
import com.igorfood.exception.EntidadeNaoEncontradaException;
import com.igorfood.exception.NegocioException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,WebRequest request){
        var status = HttpStatus.NOT_FOUND;
        Erro erro = createErroBuilder(status,ErroType.RECURSO_NAO_ENCONTRADA,ex.getMessage()).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex,WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        Erro erro = createErroBuilder(status,ErroType.ERRO_NEGOCIO,ex.getMessage()).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex,WebRequest request){
        var status = HttpStatus.CONFLICT;
        Erro erro = createErroBuilder(status,ErroType.ENTIDADE_EM_USO,ex.getMessage()).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);

    }



    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";
        ex.printStackTrace();
        Erro erro = createErroBuilder(status,ErroType.ERRO_DE_SISTEMA,detail).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto";
        BindingResult bindingResult = ex.getBindingResult();
        List<Erro.Field> erroField = bindingResult.getFieldErrors().stream()
                                        .map(fieldError ->{
                                                String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                                                return Erro.Field.builder()
                                                    .name(fieldError.getField())
                                                    .userMessage(message)
                                                    .build();
                                        })
                                        .collect(Collectors.toList());
        Erro erro = createErroBuilder((HttpStatus) status,ErroType.DADOS_INVALIDDOS,detail).timestamp(OffsetDateTime.now()).fields(erroField).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var detail = "O corpo da requisição esta inválido, verifique a sintaxe";
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException){
            return handleInvalidFormException((InvalidFormatException) rootCause,new HttpHeaders(),status,request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause,new HttpHeaders(),status,request);

        }
        Erro erro = createErroBuilder((HttpStatus) status,ErroType.MENSAGEM_INCOMPREENSIVEL,detail).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException){
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }



    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O recurso %s que você tentou acessar é inexistente",ex.getRequestURL());
        Erro erro = createErroBuilder((HttpStatus) status,ErroType.RECURSO_NAO_ENCONTRADA,detail).build();
        return handleExceptionInternal(ex,erro,headers,status,request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders httpHeaders, HttpStatus status, WebRequest request) {
        String path = ex.getPath().stream()
                .map((ref)->ref.getFieldName())
                .collect(Collectors.joining("."));
        String detail = String.format("A propriedade %s não pertence ao corpo da requisição",path);
        Erro erro = createErroBuilder((HttpStatus) status,ErroType.MENSAGEM_INCOMPREENSIVEL,detail).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErroType erroType = ErroType.PARAMETRO_INVALIDO;
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(),ex.getValue(),ex.getRequiredType());
        Erro erro = createErroBuilder(status,erroType,detail).build();
        return handleExceptionInternal(ex,erro,headers,status,request);
    }

    private ResponseEntity<Object> handleInvalidFormException(InvalidFormatException ex, HttpHeaders httpHeaders, HttpStatus status, WebRequest request) {
        String path = ex.getPath().stream()
                .map((ref)->ref.getFieldName())
                .collect(Collectors.joining("."));

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Erro erro = createErroBuilder((HttpStatus) status,ErroType.MENSAGEM_INCOMPREENSIVEL,detail).build();
        return handleExceptionInternal(ex,erro,new HttpHeaders(),status,request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus statusCode, WebRequest request) {
        if (body instanceof String) {
           body = Erro.builder()
                        .timestamp(OffsetDateTime.now())
                        .status(statusCode.value())
                        .title((String) body)
                        .build();
       }else if (body == null || body.getClass().getSimpleName().equals(("ProblemDetail"))) {
            body = Erro.builder()
                        .timestamp(OffsetDateTime.now())
                        .status(statusCode.value())
                        .title(ex.getMessage())
                        .build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private Erro.ErroBuilder createErroBuilder(HttpStatus status, ErroType erroType,String detail){
            return Erro.builder()
                    .timestamp(OffsetDateTime.now())
                    .status(status.value())
                    .title(erroType.getTitle())
                    .detail(detail);
    }
}
