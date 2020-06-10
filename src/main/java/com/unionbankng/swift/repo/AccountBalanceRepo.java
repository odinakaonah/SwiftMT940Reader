package com.unionbankng.swift.repo;

import com.unionbankng.swift.model.SwiftAccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepo extends JpaRepository<SwiftAccountBalance,Long> {


}