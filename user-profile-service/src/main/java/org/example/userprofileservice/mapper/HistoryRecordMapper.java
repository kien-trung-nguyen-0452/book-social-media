package org.example.userprofileservice.mapper;

import org.example.userprofileservice.dto.response.UserReadingHistory;
import org.example.userprofileservice.dto.response.UserReadingHistoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryRecordMapper {
    UserReadingHistory toUserReadingHistory(UserReadingHistoryResponse response);
}
