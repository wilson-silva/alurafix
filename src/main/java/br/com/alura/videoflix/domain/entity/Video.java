package br.com.alura.videoflix.domain.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identify;

    private String title;

    private String description;

    private String url;

    @ManyToOne
    private Category category;


    public Video() {

    }

    public Video(String title, String description, String url, Category category) {
        this.title = title;
        this.description = description;
        this.url = url;
        if (category.getId() == null) {
            this.category.setId(1L);
        }
        this.category = category;
    }


}
