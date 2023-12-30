package com.shop.entity;

import java.time.LocalDateTime;

import com.shop.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {
  
  @Id
  @Column(name = "item_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 50)
  private String itemNm;
  
  @Column(name = "price", nullable = false)
  private int price;

  @Column(nullable = false)
  private int stockNumber;
  
  @Lob
  @Column(nullable = false)
  private String itemDetail;
  
  @Enumerated(EnumType.STRING)
  private ItemSellStatus itemSellStatus;
  
  private LocalDateTime regTime;
  
  private LocalDateTime updateTime;

}
