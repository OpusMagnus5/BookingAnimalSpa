package pl.bodzioch.damian.dto;

import java.time.LocalDateTime;

public record AttributesDto(
        Long id,
        String username,
        String email,
        String city,
        String country,
        String phoneNumber,
        LocalDateTime createTime,
        LocalDateTime modifyTime

) {
}
