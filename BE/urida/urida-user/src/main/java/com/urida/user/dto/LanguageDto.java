package com.urida.user.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    @NotBlank
    private Long uid;
    @Max(3)
    private int language;
}
