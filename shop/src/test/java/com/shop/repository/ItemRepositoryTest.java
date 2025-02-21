package com.shop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import org.thymeleaf.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryTest {
  final String itemDetailString = "테스트 상품 상세 설명";

  @Autowired
  ItemRepository itemRepository;

  @PersistenceContext
  EntityManager em;

  @Test
  @DisplayName("상품 저장 테스트")
  public void createItemTest() {
    Item item = new Item();
    item.setItemNm("테스트 상품");
    item.setPrice(10000);
    item.setItemDetail(this.itemDetailString);
    item.setItemSellStatus(ItemSellStatus.SELL);
    item.setStockNumber(10);
    item.setRegTime(LocalDateTime.now());
    item.setUpdateTime(LocalDateTime.now());
    Item savedItem = itemRepository.save(item);
    System.out.println(savedItem.toString());
  }

  void createItemList() {
    for (int i = 0; i < 10; i++) {
      Item item = new Item();
      item.setItemNm("테스트 상품 " + i);
      item.setPrice(10000 + i);
      item.setItemDetail(this.itemDetailString + " " + i);
      item.setItemSellStatus(ItemSellStatus.SELL);
      item.setStockNumber(10 + i);
      item.setRegTime(LocalDateTime.now());
      item.setUpdateTime(LocalDateTime.now());
      itemRepository.save(item);
    }
  }

  void createItemList2() {
    for (int i = 0; i < 5; i++) {
      Item item = new Item();
      item.setItemNm("테스트 상품 " + i);
      item.setPrice(10000 + i);
      item.setItemDetail(this.itemDetailString + " " + i);
      item.setItemSellStatus(ItemSellStatus.SELL);
      item.setStockNumber(100);
      item.setRegTime(LocalDateTime.now());
      item.setUpdateTime(LocalDateTime.now());
      itemRepository.save(item);
    }
    for (int i = 5; i < 10; i++) {
      Item item = new Item();
      item.setItemNm("테스트 상품 " + i);
      item.setPrice(10000 + i);
      item.setItemDetail(this.itemDetailString + " " + i);
      item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
      item.setStockNumber(0);
      item.setRegTime(LocalDateTime.now());
      item.setUpdateTime(LocalDateTime.now());
      itemRepository.save(item);
    }
  }

  @Test
  @DisplayName("상품 조회 테스트")
  public void findByItemNmTest() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByItemNm("테스트 상품 1");

    for (Item item : itemList) {
      System.out.println(item.toString());
    }
  }

  @Test
  @DisplayName("상품 OR 테스트")
  public void findByItemNmOrItemDetailTest() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품 1", this.itemDetailString + " 3");

    for (Item item : itemList) {
      System.out.println(item);
    }
  }

  @Test
  @DisplayName("상품 가격 테스트")
  public void findByPriceLessThan() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByPriceLessThan(10002);

    for (Item item : itemList) {
      System.out.println(item);
    }
  }

  @Test
  @DisplayName("상품 가격 정렬테스트")
  public void findByPriceLessThanOrderByPriceDesc() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10002);

    for (Item item : itemList) {
      System.out.println(item);
    }
  }

  @Test
  @DisplayName("상품 @Query 테스트")
  public void findByItemDetail() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByItemDetail(this.itemDetailString);

    for (Item item : itemList) {
      System.out.println(item);
    }
  }

  @Test
  @DisplayName("상품 @Query Native 테스트")
  public void findByItemDetailByNative() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByItemDetailByNative(this.itemDetailString);

    for (Item item : itemList) {
      System.out.println(item);
    }
  }

  @Test
  @DisplayName("Querydsl 조회 테스트 1")
  public void queryDslTest() {
    this.createItemList();
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    QItem qItem = QItem.item;
    JPAQuery<Item> query = queryFactory.selectFrom(qItem)
        .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
        .where(qItem.itemDetail.like("%" + this.itemDetailString + "%"))
        .orderBy(qItem.price.desc());

    List<Item> itemList = query.fetch();

    for (Item item : itemList) {
      System.out.println(item.toString());
    }
  }

  @Test
  @DisplayName("상품 Querydsl 조회 테스트 2")
  public void querydslTest2() {
    this.createItemList2();

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QItem item = QItem.item;
    // String itemDetail = this.itemDetailString;
    int price = 10003;
    String itemSellStat = "SELL";

    booleanBuilder.and(item.itemDetail.like("%" + this.itemDetailString + "%"));
    booleanBuilder.and((item.price.gt(price)));

    if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
      booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
    }

    Pageable pageable = PageRequest.of(0, 5);
    Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
    System.out.println("total elements : " + itemPagingResult.getTotalElements());

    List<Item> resultItemList = itemPagingResult.getContent();
    for (Item resulItem : resultItemList) {
      System.out.println(resulItem.toString());
    }
  }
}
