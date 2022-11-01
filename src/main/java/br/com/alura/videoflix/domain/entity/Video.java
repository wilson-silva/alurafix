package br.com.alura.videoflix.domain.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identify;

    private String title;

    private String description;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;


}
