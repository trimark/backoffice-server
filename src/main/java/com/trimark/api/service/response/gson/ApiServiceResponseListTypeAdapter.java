package com.trimark.api.service.response.gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.trimark.api.service.response.ApiServiceResponse;

public class ApiServiceResponseListTypeAdapter<T> extends ApiServiceResponseTypeAdapter<T> {

	public ApiServiceResponseListTypeAdapter(Class<T> clazz) {
		super(clazz);
	}

	@Override
	public void write(JsonWriter out, ApiServiceResponse<?> value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ApiServiceResponse<?> createSuccessApiResponse(JsonReader in) throws IOException {
		ApiServiceResponse<List<T>> apiServiceResponse = new ApiServiceResponse<List<T>>();
		while (in.hasNext()) {
			if (in.nextName().equals("data")) {
				List<T> apiServiceResponseData = new ArrayList<T>();
				in.beginArray();
				while (in.hasNext()) {
					apiServiceResponseData.add(super.createApiServiceResponseData(in));
				}
				in.endArray();
				apiServiceResponse.setData(apiServiceResponseData);
			}
			else {
				in.skipValue();
			}
		}
		return apiServiceResponse;
	}	
}
