package com.ht.mall.controller.mall;

import com.ht.mall.entity.ItemCategory;
import com.ht.mall.form.SaveItemForm;
import com.ht.mall.service.ItemImageService;
import com.ht.mall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mall/item")
public class ItemController {

    private final ItemService itemService;
    private final ItemImageService itemImageService;

    @GetMapping("/save")
    public String save(@ModelAttribute("item")SaveItemForm form){
        form.setItemCategories(List.of(ItemCategory.values()));
        return "mall/addForm";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("item") SaveItemForm form,
                       BindingResult bindingResult,
                       @SessionAttribute("memberId") Long memberId){
        if(bindingResult.hasErrors()){
            log.info("item save error, errors={}",bindingResult);
            return "mall/addForm";
        }
        itemService.saveItem(form,memberId);

        return "redirect:/mall/addForm";
    }

    @ResponseBody
    @GetMapping("/image/{representImageName}")
    public Resource downloadFile(@PathVariable String representImageName) throws MalformedURLException {
        return new UrlResource("file:"+itemImageService.getFullPath(representImageName));
    }

}
