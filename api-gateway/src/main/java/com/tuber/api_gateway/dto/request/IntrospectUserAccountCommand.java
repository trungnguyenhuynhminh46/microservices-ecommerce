package com.tuber.api_gateway.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IntrospectUserAccountCommand {
    @NotNull(message = "Access token is required")
    String accessToken;
}
