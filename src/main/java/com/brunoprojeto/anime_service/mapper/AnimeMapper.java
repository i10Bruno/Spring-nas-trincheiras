package com.brunoprojeto.anime_service.mapper;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.request.AnimePostRequest;
import com.brunoprojeto.anime_service.request.ProducerPostRequest;
import com.brunoprojeto.anime_service.response.AnimeGetResponse;
import com.brunoprojeto.anime_service.response.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnimeMapper {


    AnimeMapper INSTANCE= Mappers.getMapper(AnimeMapper.class);
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")


    //animepostrequest vira anime
    Anime toAnimer(AnimePostRequest animePostRequest);

    //anime vira post response
    AnimePostResponse toAnimePostResponse(Anime anime);

    //anime vira get response
    AnimeGetResponse toAnimeGetResponse(Anime anime);

    //lista de anime vira lista de anime getresponse
    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> anime);

}
