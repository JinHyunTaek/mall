package com.ht.mall.controller.mall;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import com.ht.mall.entity.enumType.ItemCategory;
import com.ht.mall.service.mall.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

import static com.ht.mall.entity.enumType.ItemCategory.CLOTHES;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mall/main")
public class MainController {

    private final MainService mainService;

    @GetMapping("")
    public String main(
            @PageableDefault(sort = "id",direction = DESC) Pageable pageable,
            Model model,
            PageItemCond itemCond
    ){
        if(itemCond == null) {
            itemCond.setItemCategory(CLOTHES);
        }
        System.out.println("itemCond = " + itemCond);
        Page<ItemSimpleDto> items = mainService.setMallMain(pageable, itemCond);
        setItemPageDatas(model,items);


        List<ItemCategory> itemCategories = List.of(ItemCategory.values());
        model.addAttribute("itemCategories",itemCategories);
        model.addAttribute("itemCond",itemCond);
        return "mall/main";
    }

    private void setItemPageDatas(Model model, Page<ItemSimpleDto> items) {

        int currentPage = items.getPageable().getPageNumber();
        int startPage = (currentPage / 10) * 10;
        int endPage = Math.min((currentPage / 10) * 10 + 9, items.getTotalPages());

        model.addAttribute("currentPage",currentPage);
        model.addAttribute("hasNext", items.hasNext());
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        model.addAttribute("items",items);
    }



}
