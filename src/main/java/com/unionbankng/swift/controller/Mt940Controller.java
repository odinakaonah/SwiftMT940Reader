package com.unionbankng.swift.controller;

import com.unionbankng.swift.dto.request.SearchRequest;
import com.unionbankng.swift.dto.response.Response;
import com.unionbankng.swift.model.SwiftMt940;
import com.unionbankng.swift.service.Mt940Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mt940")
public class Mt940Controller {
    @Autowired
    private Mt940Service mt940Service;

    @GetMapping
    public ResponseEntity getAllByAccountId(@RequestParam String accountId){
        List<SwiftMt940> byAccountId = mt940Service.getByAccountId(accountId);
        if (byAccountId.isEmpty())
            return Response.setUpResponse(404,"No record found");
        return Response.setUpResponse(200,"Record Found ","",byAccountId);
    }
    @GetMapping("all")
    public ResponseEntity getAll(@RequestParam int pageNumber,@RequestParam int pageSize){
        Page<SwiftMt940> byAccountId = mt940Service.getAll(new PageRequest(pageNumber,pageSize));
        if (byAccountId==null || byAccountId.getSize()<1)
            return Response.setUpResponse(404,"No record found");
        return Response.setUpResponse(200,"Record Found ","",byAccountId);
    }
    @PostMapping("search")
    public ResponseEntity search(@RequestBody SearchRequest request, @RequestParam int pageNumber, @RequestParam int pageSize){
        Page<SwiftMt940> byAccountId = mt940Service.search(request,new PageRequest(pageNumber,pageSize));
        if (byAccountId==null || byAccountId.getTotalElements() < 1)
            return Response.setUpResponse(404,"No record found");
        return Response.setUpResponse(200,"Record Found ","",byAccountId);
    }
}
