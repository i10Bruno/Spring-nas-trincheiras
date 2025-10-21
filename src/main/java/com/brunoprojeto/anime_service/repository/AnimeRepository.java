package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime,Long> {
}
