package com.urida.user.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    @NotNull
    private Long uid;
    @Max(3)
    private int language;
}
