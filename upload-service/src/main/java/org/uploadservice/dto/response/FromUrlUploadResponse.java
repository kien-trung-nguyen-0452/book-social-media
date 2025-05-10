package org.uploadservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FromUrlUploadResponse {
    String url;
    String public_id;
}
