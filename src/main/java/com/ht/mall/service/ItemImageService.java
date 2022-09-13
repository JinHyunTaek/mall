package com.ht.mall.service;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.ItemImage;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.exeption.ErrorCode;
import com.ht.mall.repository.item.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ht.mall.exeption.ErrorCode.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;

    @Value("${file.dir}")
    public String imageDir;

    public String getFullPath(String representImageName){
        return imageDir +representImageName;
    }

    @Transactional
    public ItemImage saveItemImages(List<MultipartFile> multipartFiles, Item item) {
        List<ItemImage> itemImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            ItemImage itemImage = saveImage(multipartFile, item);
            itemImageRepository.save(itemImage);
            itemImages.add(itemImage);
        }
        return itemImages.get(0);
    }

    @Transactional
    public ItemImage saveImage(MultipartFile multipartFile, Item item)  {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalImageName = multipartFile.getOriginalFilename();
        String storedImageName = createStoreImageName(originalImageName);
        String fullPath = getFullPath(storedImageName);

        try{
            multipartFile.transferTo(new java.io.File(fullPath));
        }catch (IOException e){
            throw new BasicException(e,INTERNAL_SERVER_ERROR);
        }

        return ItemImage.builder()
                .item(item)
                .originalImageName(originalImageName)
                .storedImageName(storedImageName)
                .build();
    }

    public void deleteImagesInDirectory(List<ItemImage> images) {
        for (ItemImage itemImage : images) {
            String storedImageName = getFullPath(itemImage.getStoredImageName());
            java.io.File storedImage = new java.io.File(getFullPath(storedImageName));
            storedImage.delete();
        }
    }

    private String createStoreImageName(String originalImageName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalImageName);
        return uuid + "." + ext;
    }

    private String extractExt(String originalImageName) {
        int pos = originalImageName.lastIndexOf(".");
        String ext = originalImageName.substring(pos + 1);
        return ext;
    }

}
