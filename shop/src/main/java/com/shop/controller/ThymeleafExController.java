package com.shop.controller;

import com.shop.dto.ItemDto;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model) {
        Map<String, Object> attribs = new HashMap<String, Object>();
        attribs.put("data", "타임리프 예제...");
        attribs.put("title", "Thymeleaf title");
        model.addAllAttributes(attribs);

        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("테스트 상품 설명");
        itemDto.setItemNm("테스트 상품");
        itemDto.setPrice(1000);
        itemDto.setRegTime(LocalDateTime.now());

        Map<String, Object> attribs = new HashMap<String, Object>();
        attribs.put("data", "타임리프 예제...");
        attribs.put("title", "Thymeleaf title");
        attribs.put("itemDto", itemDto);
        model.addAllAttributes(attribs);

        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<ItemDto>();

        for (int i = 0; i < 10; ++i) {
            ItemDto itemDto = new ItemDto();

            itemDto.setItemDetail("테스트 상품 설명 " + i);
            itemDto.setItemNm("테스트 상품 " + i);
            itemDto.setPrice(1000 + i * 100);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        Map<String, Object> attribs = new HashMap<String, Object>();
        attribs.put("data", "타임리프 예제...");
        attribs.put("title", "Thymeleaf title");
        attribs.put("itemDtoList", itemDtoList);
        model.addAllAttributes(attribs);

        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping(value = "/ex07")
    public String thymeleafExample07() {
        return "thymeleafEx/thymeleafEx07";
    }
}
