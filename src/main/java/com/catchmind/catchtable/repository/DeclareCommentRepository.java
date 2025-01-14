package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.DeclareComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeclareCommentRepository extends JpaRepository<DeclareComment, Long> {
    Page<DeclareComment> findAllByProfile_PrIdx(Pageable pageable, Long prIdx);
}
