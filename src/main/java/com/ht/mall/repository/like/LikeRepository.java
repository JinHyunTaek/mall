package com.ht.mall.repository.like;

import com.ht.mall.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Long countByItemId(Long itemId);

    boolean existsByMemberIdAndItemId(@Param("memberId") Long memberId,@Param("itemId") Long itemId);

    void deleteByMemberIdAndItemId(@Param("memberId") Long memberId,@Param("itemId") Long itemId);
}
