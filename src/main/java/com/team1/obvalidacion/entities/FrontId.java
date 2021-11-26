package com.team1.obvalidacion.entities;

import javax.persistence.*;

@Entity
public class FrontId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column private
    Long id;

    @Column
    private String url;

    @Column
    private String cloudinaryId;

    public FrontId() {
    }

    public FrontId(String url, String cloudinaryId) {
        this.url = url;
        this.cloudinaryId = cloudinaryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCloudinaryId() {
        return cloudinaryId;
    }

    public void setCloudinaryId(String cloudinaryId) {
        this.cloudinaryId = cloudinaryId;
    }

    @Override
    public String toString() {
        return "FrontId{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", cloudinaryId='" + cloudinaryId + '\'' +
                '}';
    }
}
