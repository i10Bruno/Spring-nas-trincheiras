package com.brunoprojeto.anime_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
//comparar obj pelos atributos  e não pela memoria
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Anime {
    @EqualsAndHashCode.Include
    @JsonProperty("full_name")
    @Column(nullable = false)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
