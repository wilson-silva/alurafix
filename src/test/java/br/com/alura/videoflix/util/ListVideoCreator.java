package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Video;

import java.util.List;

public class ListVideoCreator {

    public static List<VideoResponse> createListResponse(){
        return List.of(VideoCreator.createVideoResponse());
    }

    public static List<Video> createListVideo(){
        return List.of(VideoCreator.createVideo());
    }
}
