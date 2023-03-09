package com.urida.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {

    @NotEmpty
    @Size(min=2,max=10)
    private String nickname;

    @NotBlank
    private String social_id;

    @Max(3)
    private int language;

    @NotBlank
    @Size(min=1, max=5)
    private String type;
}
