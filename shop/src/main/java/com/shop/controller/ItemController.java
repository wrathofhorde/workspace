package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shop.dto.ItemFormDto;

@Controller
public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("ItemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

}
