package com.shop.repository;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryTest {

  @Autowired
  ItemRepository itemRepository;

  @Test
  @DisplayName("상품 저장 테스트")
  public void createItemTest() {
    Item item = new Item();
    item.setItemNm("테스트 상품");
    item.setPrice(10000);
    item.setItemDetail("테스트 상품 상세 설명");
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
      item.setItemDetail("테스트 상품 상세 설명 " + i);
      item.setItemSellStatus(ItemSellStatus.SELL);
      item.setStockNumber(10 + i);
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
}
