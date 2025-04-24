package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import jakarta.persistence.*;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgUrl;

    private String imgName;

    private String repImgYn;

    private String oriImgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.imgUrl = imgUrl;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
    }

}
