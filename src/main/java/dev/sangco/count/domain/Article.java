package dev.sangco.count.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Article {

    @Id
    private String uri;

    @Lob
    @Column( length = 100000 )
    private String contents;

    public Article() {
    }

    public Article(String uri, String contents) {
        this.uri = uri;
        this.contents = contents;
    }
}
