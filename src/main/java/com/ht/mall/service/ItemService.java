package com.ht.mall.service;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.ItemImage;
import com.ht.mall.entity.Member;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.exeption.ErrorCode;
import com.ht.mall.form.item.ItemDetailForm;
import com.ht.mall.form.item.SaveItemForm;
import com.ht.mall.repository.item.ItemImageRepository;
import com.ht.mall.repository.item.ItemRepository;
import com.ht.mall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;
    private final ItemImageRepository itemImageRepository;

    @Transactional
    public void saveItem(SaveItemForm form, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(ErrorCode.NO_MEMBER_FOUND));
        form.setMember(member);
        Item item = form.toEntity();
        ItemImage representImage = itemImageService.saveItemImages(form.getImageFiles(), item);
        item.setRepresentImage(representImage);
        itemRepository.save(item);
    }

    public ItemDetailForm detail(ItemDetailForm form, Long itemId){
        Item item = itemRepository.findWithMemberById(itemId);
        List<ItemImage> itemImages = itemImageRepository.findByItemId(itemId);
         return form.toForm(item, itemImages);
    }

}
