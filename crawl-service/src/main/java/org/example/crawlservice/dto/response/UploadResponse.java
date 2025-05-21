package org.example.crawlservice.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UploadResponse {
    String filename;
    List<String> url;
}
