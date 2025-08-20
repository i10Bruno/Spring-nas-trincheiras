package com.brunoprojeto.anime_service.mapper;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.request.AnimePostRequest;
import com.brunoprojeto.anime_service.request.ProducerPostRequest;
import com.brunoprojeto.anime_service.response.AnimeGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface AnimeMapper {
    AnimeMapper INSTANCE= Mappers.getMapper(AnimeMapper.class);
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Anime toAnimer(AnimePostRequest animePostRequest);


    AnimeGetResponse toAnimeGetResponse(Anime anime);

}
