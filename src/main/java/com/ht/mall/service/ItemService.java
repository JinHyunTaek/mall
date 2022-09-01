package com.ht.mall.service;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.ItemImage;
import com.ht.mall.entity.Member;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.form.SaveItemForm;
import com.ht.mall.repository.ItemRepository;
import com.ht.mall.repository.MemberRepository;
import com.ht.mall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;


    @Transactional
    public void saveItem(SaveItemForm form, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException("no member"));
        form.setMember(member);
        Item item = form.toEntity();
        ItemImage representImage = itemImageService.saveItemImages(form.getImageFiles(), item);
        item.setRepresentImage(representImage);
        itemRepository.save(item);
    }

}
