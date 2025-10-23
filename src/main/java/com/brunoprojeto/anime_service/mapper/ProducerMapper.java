package com.brunoprojeto.anime_service.mapper;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.request.ProducerPostRequest;
import com.brunoprojeto.anime_service.request.ProducerPutRequest;
import com.brunoprojeto.anime_service.response.ProducerGetResponse;
import com.brunoprojeto.anime_service.response.ProducerPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProducerMapper {


    Producer toProducer(ProducerPostRequest postRequest);

    ProducerPostResponse producerPostResponse(Producer producer);

    ProducerGetResponse toProducerGetResponse(Producer producer);

    Producer toProducerPutRequest(ProducerPutRequest producerPutRequest);

    List<ProducerGetResponse> toProducerGetResponselist(List<Producer> producer);

}





