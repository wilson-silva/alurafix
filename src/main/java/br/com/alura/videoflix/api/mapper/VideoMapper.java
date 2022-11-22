package br.com.alura.videoflix.api.mapper;

import br.com.alura.videoflix.api.request.VideoRequest;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Video;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VideoMapper {

    private final ModelMapper mapper;

    public Video toVideo(VideoRequest request) {
        return mapper.map(request, Video.class);
    }

    public VideoResponse toVideoResponse(Video video) {
        return mapper.map(video, VideoResponse.class);
    }

    public List<VideoResponse> toVideoResponseList(List<Video> videos){
        return videos.stream()
                .map(this::toVideoResponse)
                .collect(Collectors.toList());
    }
}
