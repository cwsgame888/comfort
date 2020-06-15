package com.core.common.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataMapArgumentResolver implements HandlerMethodArgumentResolver {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return DataMap.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		DataMap<String, Object> dataMap = new DataMap<String, Object>();

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		
		// 컨텐트 타입을 보고 json타입 처리 로직 태우도록 함
		String contentType = request.getContentType();
		if(contentType == null) {
			contentType = "";
		}
		if(contentType.contains("application/json")){
			// requestBody를 읽어옴
			StringBuffer jsonData = new StringBuffer();
			jsonData.append(getBody(request));
			log.debug("################ 제이슨 데이터 #################");
			log.debug(jsonData.toString());
			log.debug("###########################################");
			try {
				// 잭슨에서 제공하는 함수를 사용해서 map 형식으로 변환(단건)
				@SuppressWarnings("unchecked")
				DataMap<String, String> map = new ObjectMapper().readValue(jsonData.toString(), new DataMap<String, String>().getClass()) ;
				if(log.isDebugEnabled()){
					log.debug("단건 json 처리");
				}
				dataMap.putAll(map);
			} catch (JsonMappingException e) {
				// 잭슨에서 제공하는 함수를 사용해서 list-map 형식으로 변환(다건)
				ArrayList<DataMap<String, String>> list = new ObjectMapper().readValue(jsonData.toString(), new TypeReference<ArrayList<DataMap<String, String>>>() {}) ;
				if(log.isDebugEnabled()){
					log.debug("다건 json 처리");
				}
				dataMap.put("ArrayData", list);
			}
		} else {
			Enumeration<?> enumeration = request.getParameterNames();
			String key = null;
			String[] values = null;
			while (enumeration.hasMoreElements()) {
				key = (String) enumeration.nextElement();
				values = request.getParameterValues(key);
				if(log.isDebugEnabled()){
					log.debug("key:::::[" + key + "], value:::::[" + ((values.length > 1) ? values : values[0]) + "]");
				}
				if (values != null) {
					dataMap.put(key, (values.length > 1) ? values : values[0]);
				}
			}
		}
		
		return dataMap;
	}
	
	/* 요청 헤더의 content-type이 'application/json' 이거나 'multipart/form-data' 형식일 때,
	 * 혹은 이름 없이 값만 전달될 때 이 값은 요청 헤더가 아닌 바디를 통해 전달된다. 
	 * 이러한 형태의 값을 'payload body'라고 하는데 요청 바디에 직접 쓰여진다 하여 'request body post data'라고도 한다.
	 * 서블릿에서 payload body는 파라미터를 다루는 일반적인 방법, 즉 Request.getParameter()로 가져올 수 없고 
	 * Request.getInputStream() 혹은 Request.getReader()를 통해 body를 직접 읽는 방식으로 가져온다. 
	 * 참고로 payload body가 아닌 파라미터는 GET 방식에선 주소에 포함되고 POST 방식에선 헤더에 포함된다.*/
	public static String getBody(HttpServletRequest request) throws IOException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}
}