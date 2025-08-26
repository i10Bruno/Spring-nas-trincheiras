package com.brunoprojeto.anime_service.mapper;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.request.ProducerPostRequest;
import com.brunoprojeto.anime_service.request.ProducerPutRequest;
import com.brunoprojeto.anime_service.response.ProducerGetResponse;
import com.brunoprojeto.anime_service.response.ProducerPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProducerMapper {

    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now()")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Producer toProducer(ProducerPostRequest postRequest);

    ProducerPostResponse producerPostResponse(Producer producer);

    ProducerGetResponse toProducerGetResponse(Producer producer);
    Producer toProducerPutRequest(ProducerPutRequest producerPutRequest , LocalDateTime createdAt);
    List<ProducerGetResponse> toProducerGetResponselist(List<Producer> producer);

}





