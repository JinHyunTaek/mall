package com.ht.mall.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Item extends BaseEntity{

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_image_id")
    private ItemImage representImage;

    @Enumerated(value = STRING)
    private ItemCategory itemCategory;

    private String itemName;

    private Integer price;

    private Integer stock; //재고

    private String description;

    public void updateStock(Integer stock){
        this.stock = stock;
    }

}
