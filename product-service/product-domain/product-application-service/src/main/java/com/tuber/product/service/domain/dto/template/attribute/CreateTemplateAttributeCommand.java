package com.tuber.product.service.domain.dto.template.attribute;

import com.tuber.product.service.domain.dto.shared.TemplateAttributeOption;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateTemplateAttributeCommand {
    @NotNull
    @NotBlank(message = "Template attribute name is mandatory")
    String name;
    @NotNull
    @NotBlank(message = "Template attribute default value is mandatory")
    String defaultValue;
    @NotNull(message = "You must add at least one option to the attribute")
    List<TemplateAttributeOption> options;
}
