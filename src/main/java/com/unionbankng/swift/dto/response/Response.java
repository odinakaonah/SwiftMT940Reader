package com.unionbankng.swift.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unionbankng.swift.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@Slf4j
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Response implements Serializable {

  private String statusCode;
  private String statusMessage;
  private Boolean isAccountText;
  private Object data;

  public Response(Integer statusCode, String statusMessage, Object data) {
    this.statusCode = (statusCode==202 || statusCode ==200 ? "00" :String.valueOf(statusCode));
    this.statusMessage = statusMessage;
    this.data = data;
  }
  public Response(String statusCode, String statusMessage, Object data) {
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
    this.data = data;
  }
  public static ResponseEntity setUpResponse(Integer httpCode, String statusMessage, String replace, Object  obj){
    Response responseMessage = new Response(httpCode,statusMessage.replace("{}",replace), obj);
    return ResponseEntity.status(httpCode).body(responseMessage);
  }
  public static ResponseEntity setUpResponse(Integer httpCode, String statusMessage){
    Response responseMessage = new Response(httpCode,statusMessage, null);
    return ResponseEntity.status(httpCode).body(responseMessage);
  }
  public static ResponseEntity setUpResponse(ResponseCode resonseCode, String replace, Object  obj){
    Response responseMessage = new Response(resonseCode.getCode(), resonseCode.getValue().replace("{}",replace), obj);
    return ResponseEntity.status(resonseCode.getCode()).body(responseMessage);
  }
  public static ResponseEntity setUpResponse(ResponseCode resonseCode){
    Response responseMessage = new Response(resonseCode.getCode(),resonseCode.getValue(), null);
    return ResponseEntity.status(resonseCode.getCode()).body(responseMessage);
  }
  public static Response setUpResponse(ResponseCode resonseCode, String replace){
    return new Response(resonseCode.getCode(), resonseCode.getValue().replace("{}",replace), null);

  }

}