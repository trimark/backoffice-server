package com.trimark.api.service;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trimark.api.service.request.ApiServiceRequest;
import com.trimark.api.service.request.ApiServiceRequestParamConfig;
import com.trimark.api.service.response.ApiServiceResponse;
import com.trimark.api.service.response.gson.ApiServiceResponseListTypeAdapter;
import com.trimark.api.service.response.gson.ApiServiceResponseTypeAdapter;

@Component
public class ApiService {
	
	public <X extends ApiServiceRequest, Y> ApiServiceResponse<?> execute(X apiServiceRequest, final Class<Y> clazz)
	throws Exception {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			URIBuilder uriBuilder = this.createURIBuilder(apiServiceRequest);
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			return httpClient.execute(httpGet, new ApiServiceResponseHandler<Y>(new ApiServiceResponseTypeAdapter<Y>(clazz)));
	    }
	}
	
	public <X extends ApiServiceRequest, Y> ApiServiceResponse<?> executeList(X apiServiceRequest, final Class<Y> clazz)
	throws Exception {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			URIBuilder uriBuilder = this.createURIBuilder(apiServiceRequest);
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			System.out.println("DAMONGKAL >>> " + uriBuilder.build().toString());
			return httpClient.execute(httpGet, new ApiServiceResponseHandler<Y>(new ApiServiceResponseListTypeAdapter<Y>(clazz)));
	    }
	}
	
	private URIBuilder createURIBuilder(ApiServiceRequest apiServiceRequest) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(apiServiceRequest.getPath());
		PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(apiServiceRequest.getClass());
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			if (!descriptor.getName().equals("class")) {
				Method method = descriptor.getReadMethod();
				Object paramValue = method.invoke(apiServiceRequest, (Object[]) null);
				if (paramValue != null) {
					if (method.isAnnotationPresent(ApiServiceRequestParamConfig.class)) {
						ApiServiceRequestParamConfig serviceRequestParamConfig = method.getAnnotation(ApiServiceRequestParamConfig.class);
						if (serviceRequestParamConfig.name().length() > 0) {
							uriBuilder.addParameter(serviceRequestParamConfig.name(), paramValue.toString());
						}
					}
					else {
						uriBuilder.addParameter(descriptor.getName(), paramValue.toString());
					}
				}
			}
		}
		return uriBuilder;
	}
	
	private class ApiServiceResponseHandler<Y> implements ResponseHandler<ApiServiceResponse<?>> {
		
		private ApiServiceResponseTypeAdapter<Y> apiServiceResponseTypeAdapter;
		
		public ApiServiceResponseHandler(ApiServiceResponseTypeAdapter<Y> apiServiceResponseTypeAdapter) {
			this.apiServiceResponseTypeAdapter = apiServiceResponseTypeAdapter;
		}

		@Override
		public ApiServiceResponse<?> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			HttpEntity entity = response.getEntity();
			
			if (statusLine.getStatusCode() >= 300)
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			
			if (entity == null)
				throw new ClientProtocolException("Response contains no content");
			
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(ApiServiceResponse.class, apiServiceResponseTypeAdapter);
			Gson gson = gsonBuilder.create();
			
			String responseData = EntityUtils.toString(entity);
			System.out.println("ApiService responseData >>> " + responseData);
			return gson.fromJson(responseData, ApiServiceResponse.class);
		}
		
	}
}
