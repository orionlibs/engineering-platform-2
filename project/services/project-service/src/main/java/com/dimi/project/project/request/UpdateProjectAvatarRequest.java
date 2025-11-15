package com.dimi.project.project.request;

import com.dimi.core.api.APIRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class UpdateProjectAvatarRequest extends APIRequest
{
    @NotBlank(message = "avatarURL must not be blank")
    @Pattern(regexp = "https?://[\\w.-]+(?:\\.[\\w.-]+)+[/\\w\\-._~:?#[\\\\]@!$&'()*+,;=]*")
    private String avatarURL;
}
