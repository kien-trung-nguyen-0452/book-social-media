package org.example.crawlservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.crawlservice.dto.response.CrawlResponse;
import org.example.crawlservice.dto.data.Metadata;
import org.example.crawlservice.service.CrawlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawl")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrawlController {
    CrawlService crawlService;

    @PostMapping
    public ResponseEntity<CrawlResponse> crawl (@RequestBody Metadata metadata){
        CrawlResponse crawlResponse = crawlService.crawl(metadata);
        return new ResponseEntity<>(crawlResponse,HttpStatus.OK);
    }

}
