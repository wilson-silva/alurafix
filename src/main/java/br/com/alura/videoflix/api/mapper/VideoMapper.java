package br.com.alura.videoflix.api.mapper;

import br.com.alura.videoflix.api.request.VideoRequest;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoMapper {

    public static Video toVideo(VideoRequest request) {
        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setUrl(request.getUrl());
        return video;
    }

    public static VideoResponse toVideoResponse(Video video) {
        VideoResponse response = new VideoResponse();
        response.setId(video.getId());
        response.setTitle(video.getTitle());
        response.setDescription(video.getDescription());
        response.setUrl(video.getUrl());
        return response;
    }

    public static List<VideoResponse> toVideoResponseList(List<Video> videos){
        List<VideoResponse> responses = new ArrayList<>();
        for(Video video : videos){
            responses.add(toVideoResponse(video));
        }
        return responses;
    }
}
