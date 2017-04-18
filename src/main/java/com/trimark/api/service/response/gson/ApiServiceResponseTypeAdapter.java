package com.trimark.api.service.response.gson;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.trimark.api.service.response.ApiServiceResponse;
import com.trimark.api.service.response.ApiServiceResponseParamConfig;

public class ApiServiceResponseTypeAdapter<T> extends TypeAdapter<ApiServiceResponse<?>> {	
	protected Class<T> clazz;

	public ApiServiceResponseTypeAdapter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void write(JsonWriter out, ApiServiceResponse<?> value) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ApiServiceResponse<?> read(JsonReader in) throws IOException {
		ApiServiceResponse<?> result = null;
		int code = 0;
		
		in.beginObject();
		while (in.hasNext()) {
			if (in.nextName().equals("code")) {
				code = in.nextInt();
				break;
			}
			else {
				in.skipValue();
			}
		}
		
		if (code != 0) {
			result = this.createErrorApiServiceResponse(in);
			result.setCode(code);
		}
		else {
			result = this.createSuccessApiResponse(in);
		}
		
		in.endObject();
		return result;
	}
	
	protected ApiServiceResponse<?> createSuccessApiResponse(JsonReader in) throws IOException {
		ApiServiceResponse<T> apiServiceResponse = new ApiServiceResponse<T>();
		while (in.hasNext()) {
			if (in.nextName().equals("data")) {
				apiServiceResponse.setData(createApiServiceResponseData(in));
			}
			else {
				in.skipValue();
			}
		}
		return apiServiceResponse;
	}
	
	protected T createApiServiceResponseData(JsonReader in) throws IOException {
		T apiServiceResponseData = BeanUtils.instantiate(clazz);
		in.beginObject();
		while (in.hasNext()) {
			String nextName = in.nextName();
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(clazz, nextName);
			if (descriptor == null)
			{
				PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(clazz);
				for (PropertyDescriptor p : propertyDescriptors) {
					if (!p.getName().equals("class")) {
						Method method = p.getWriteMethod();
						if (method.isAnnotationPresent(ApiServiceResponseParamConfig.class)) {
							ApiServiceResponseParamConfig apiServiceResponseParamConfig = method.getAnnotation(ApiServiceResponseParamConfig.class);
							if (apiServiceResponseParamConfig.name().equals(nextName)) {
								descriptor = p;
								break;
							}
						}
					}
				}
			}
			if (descriptor != null) {
				if (in.peek() == JsonToken.NULL) {
					in.skipValue();
				}
				else {
					Object apiServiceResponseDataValue = null;
					switch(descriptor.getReadMethod().getReturnType().getName()) {
					case "int":
						apiServiceResponseDataValue = in.nextInt();
						break;
					case "long":
						apiServiceResponseDataValue = in.nextLong();
						break;
					case "java.lang.String":
						apiServiceResponseDataValue = in.nextString();
						break;
					}
					try {
						descriptor.getWriteMethod().invoke(apiServiceResponseData, apiServiceResponseDataValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			}
			else {
				in.skipValue();
			}
		}
		in.endObject();
		return apiServiceResponseData;
	}
	
	protected ApiServiceResponse<String> createErrorApiServiceResponse(JsonReader in) throws IOException {
		ApiServiceResponse<String> apiServiceResponse = new ApiServiceResponse<String>();
		
		while (in.hasNext()) {
			if (in.nextName().equals("msg")) {
				apiServiceResponse.setData(in.nextString());
			}
			else {
				in.skipValue();
			}				
		}
		
		return apiServiceResponse;
	}
}
