package com.unionbankng.swift.repo;

import com.unionbankng.swift.model.SwiftMt940;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Mt940Repo extends JpaRepository<SwiftMt940,Long> {
    @Query("select m from SwiftMt940 m where m.accIdentification =?1")
    List<SwiftMt940> getAllByAccIdentification(String name);

    @Query("select m from SwiftMt940 m where m.transRefNum =?1")
    SwiftMt940 getTransRefNum(String transRefNum);

    @Query("select s from SwiftMt940  s")
    Page<SwiftMt940> findAllMessages(Pageable pageable);

}
