package com.unionbankng.swift.repo;

import com.unionbankng.swift.dto.request.SearchRequest;
import com.unionbankng.swift.model.SwiftMt940;
import com.unionbankng.swift.utils.CommonUtils;
import com.unionbankng.swift.utils.Validation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchRepo {
    @PersistenceContext
    private EntityManager entityManager;
//    Logger log = LoggerFactory.getLogger(SearchRepo.class);


    public Page<SwiftMt940> searchMt940(SearchRequest request, Pageable pageable){
        log.info("Search Request {}",request);
        List<SwiftMt940> swiftMt940s = new ArrayList<>();
        boolean checked = false;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<SwiftMt940> cq = cb.createQuery(SwiftMt940.class);
        Root<SwiftMt940> root = cq.from(SwiftMt940.class);

        //create a base predicate to hold all the queries
        Predicate predicate = cb.like(root.get("transRefNum"), "%%");

        //date_created provided but status NOT provided
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if(!StringUtils.isEmpty(request.getTransRefNum())){
            checked = true;
            predicate = cb.and(predicate,cb.like(root.get("transRefNum"), "%" + request.getTransRefNum() + "%"));
        }
        if(!StringUtils.isEmpty(request.getTransRelatedRefNum())){
            checked = true;
            predicate = cb.and(predicate,cb.like(root.get("transRelatedRefNum"), "%" + request.getTransRelatedRefNum() + "%"));
        }
        Join statementLine = root.join("statementLine");
        if(!StringUtils.isEmpty(request.getDebitCreditMark())){
            checked = true;
            predicate = cb.and(predicate,cb.like(statementLine.get("debitCreditMark"), "%" + request.getDebitCreditMark() + "%"));
        }
        if(!StringUtils.isEmpty(request.getFundsCode())){
            checked = true;
            predicate = cb.and(predicate,cb.like(statementLine.get("fundsCode"), "%" + request.getFundsCode() + "%"));
        }
        if(!StringUtils.isEmpty(request.getAmount())){
            checked = true;
            predicate = cb.and(predicate,cb.like(statementLine.get("amount"), "%" + request.getAmount() + "%"));
        }
        if (Validation.validData(request.getStartDate()) && Validation.validData(request.getEndDate())) {
            checked = true;
            LocalDate fromDate = CommonUtils.startOfDay(request.getStartDate());
            LocalDate toDate = CommonUtils.endOfDay(request.getEndDate());
            predicate = cb.and(predicate,cb.between(statementLine.get("valueDate"),fromDate,toDate));
        }

        if(!StringUtils.isEmpty(request.getAccIdentification())){
            checked = true;
            predicate = cb.and(predicate,cb.like(root.get("accIdentification"), "%" + request.getAccIdentification() + "%"));
        }

        Join fundamentalReference = root.join("fundamentalReference");
        if(!StringUtils.isEmpty(request.getLogicalTerminal())){
            checked = true;
            predicate = cb.and(predicate,cb.like(fundamentalReference.get("logicalTerminal"), "%" + request.getMirLogicalTerminal() + "%"));
        }

        Join messageSenderInfo = root.join("messageSenderInfo");
        if(!StringUtils.isEmpty(request.getMirLogicalTerminal())){
            checked = true;
            predicate = cb.and(predicate,cb.like(messageSenderInfo.get("mirLogicalTerminal"), "%" + request.getMirLogicalTerminal() + "%"));
        }

        if(!StringUtils.isEmpty(request.getMirSessionNumber())){
            checked = true;
            predicate = cb.and(predicate,cb.like(messageSenderInfo.get("mirSessionNumber"), "%" + request.getMirSessionNumber() + "%"));
        }

        if(!StringUtils.isEmpty(request.getMirSequenceNumber())){
            checked = true;
            predicate = cb.and(predicate,cb.like(messageSenderInfo.get("mirSequenceNumber"), "%" + request.getMirSequenceNumber() + "%"));
        }
//
//        Join openingBalance = root.join("openingBalance");
//        if(!StringUtils.isEmpty(request.getCurrency())){
//            checked = true;
//            predicate = cb.and(predicate,cb.like(openingBalance.get("currency"), "%" + request.getCurrency() + "%"));
//        }

        if (checked) {
            System.out.println("predicate "+predicate.toString());
            cq.where(predicate);
            System.out.println("Criterial Builder "+cq.toString());
            TypedQuery<SwiftMt940> query = entityManager.createQuery(cq);

            swiftMt940s = query.getResultList();

        }
        return new PageImpl<>(swiftMt940s, pageable, swiftMt940s.size());

    }
}
