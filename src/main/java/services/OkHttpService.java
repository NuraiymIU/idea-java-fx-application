package services;

import models.Idea;
import models.response.ResponseDto;
import services.impl.OkHttpServiceImpl;

import java.util.List;

public interface OkHttpService {
    OkHttpService INSTANCE = new OkHttpServiceImpl();
    ResponseDto sendIdea(Idea idea);
    List<Idea> getAllIdeas(String phone);
}
