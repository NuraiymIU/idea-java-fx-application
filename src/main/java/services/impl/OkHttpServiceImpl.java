package services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Idea;
import models.response.ResponseDto;
import okhttp3.*;
import services.OkHttpService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OkHttpServiceImpl implements OkHttpService {

    private OkHttpClient okHttpClient = new OkHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResponseDto sendIdea(Idea idea) {
        try {
            String requestJson = objectMapper.writeValueAsString(idea);
            RequestBody requestBody = RequestBody.create(
                    requestJson,
                    MediaType.parse("application/json; charset=UTF-8")
            );

            Request request = new Request.Builder()
                    .url("http://localhost:8082/api/v1/idea/save")
                    .post(requestBody)
                    .build();

            Call call = okHttpClient.newCall(request);

            Response response = call.execute();
            if(response.isSuccessful()) {
                ResponseDto responseDto = objectMapper.readValue(response.body().string(), ResponseDto.class);
                return  responseDto;
            }
            return  new ResponseDto();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Idea> getAllIdeas(String phone) {
        try {
            HttpUrl.Builder httpUrl = HttpUrl.parse("http://localhost:8082/api/v1/idea/getUserIdeas").newBuilder();
            httpUrl.addQueryParameter("phone", phone);
            Request request = new Request.Builder().url(httpUrl.build().url()).build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if(response.isSuccessful()) {
                List<Idea> ideaList = objectMapper.readValue(response.body().string(), new TypeReference<List<Idea>>() {});
                return ideaList;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<Idea>();
    }
}
