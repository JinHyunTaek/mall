package com.ht.mall.service.mall;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.ItemImage;
import com.ht.mall.entity.Like;
import com.ht.mall.entity.Member;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.exeption.ErrorCode;
import com.ht.mall.form.item.ItemDetailForm;
import com.ht.mall.form.item.SaveItemForm;
import com.ht.mall.repository.item.ItemImageRepository;
import com.ht.mall.repository.item.ItemRepository;
import com.ht.mall.repository.member.MemberRepository;
import com.ht.mall.repository.like.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ht.mall.exeption.ErrorCode.NO_ITEM_FOUND;
import static com.ht.mall.exeption.ErrorCode.NO_MEMBER_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;
    private final ItemImageRepository itemImageRepository;
    private final LikeRepository likeRepository;

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

    @Transactional
    public void delete(Long itemId){
        List<ItemImage> itemImages = itemImageRepository.findByItemId(itemId);
        itemImageService.deleteImagesInDirectory(itemImages);
        itemRepository.deleteById(itemId);
    }

    public ItemDetailForm detail(ItemDetailForm form, Long itemId){
        Item item = itemRepository.findWithMemberById(itemId);
        List<ItemImage> itemImages = itemImageRepository.findByItemId(itemId);
        Long likeCount = likeRepository.countByItemId(itemId);
        return form.toForm(item, itemImages,likeCount);
    }

    @Transactional
    public void saveOrDeleteLike(Long itemId, Long memberId){
        boolean ifExists = likeRepository.existsByMemberIdAndItemId(memberId,itemId);
        if(ifExists){
            likeRepository.deleteByMemberIdAndItemId(memberId,itemId);
            return;
        }
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BasicException(NO_ITEM_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));
        Like like = Like.builder()
                .item(item)
                .member(member)
                .build();
        likeRepository.save(like);
    }

}
