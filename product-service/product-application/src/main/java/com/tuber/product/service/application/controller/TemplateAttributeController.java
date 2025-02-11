package com.tuber.product.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.validators.ValidUUID;
import com.tuber.product.service.domain.dto.template.attribute.CreateTemplateAttributeCommand;
import com.tuber.product.service.domain.dto.template.attribute.GetTemplateAttributeQuery;
import com.tuber.product.service.domain.dto.template.attribute.ModifyTemplateAttributeCommand;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributeResponseData;
import com.tuber.product.service.domain.ports.input.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/${service.name}/template/attributes", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class TemplateAttributeController {
    private final ProductApplicationService productApplicationService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TEMPLATE')")
    public ResponseEntity<ApiResponse<TemplateAttributeResponseData>> createTemplateAttribute(@RequestBody CreateTemplateAttributeCommand createTemplateAttributeCommand) {
        ApiResponse<TemplateAttributeResponseData> createTemplateAttributeResponse = productApplicationService.createTemplateAttribute(createTemplateAttributeCommand);
        log.info("Successfully created template attribute with name {}", createTemplateAttributeCommand.getName());
        return ResponseEntity.ok(createTemplateAttributeResponse);
    }

    @GetMapping("/{templateAttributeId}")
    public ResponseEntity<ApiResponse<TemplateAttributeResponseData>> getSingle(@PathVariable @ValidUUID String templateAttributeId) {
        GetTemplateAttributeQuery getTemplateAttributeQuery = GetTemplateAttributeQuery.builder().id(UUID.fromString(templateAttributeId)).build();
        ApiResponse<TemplateAttributeResponseData> templateAttributeResponse = productApplicationService.getSingleTemplateAttribute(getTemplateAttributeQuery);
        log.info("Successfully fetched template attribute with id {}", templateAttributeId);
        return ResponseEntity.ok(templateAttributeResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TemplateAttributeResponseData>> getAll() {
        ApiResponse<TemplateAttributeResponseData> templateAttributesResponse = productApplicationService.getAllTemplateAttributes();
        log.info("Successfully fetched all template attributes");
        return ResponseEntity.ok(templateAttributesResponse);
    }

    @PatchMapping("/{templateAttributeId}")
    @PreAuthorize("hasAuthority('UPDATE_TEMPLATE')")
    public ResponseEntity<ApiResponse<TemplateAttributeResponseData>> update(@PathVariable @ValidUUID String templateAttributeId, @RequestBody ModifyTemplateAttributeCommand modifyTemplateAttributeCommand) {
        modifyTemplateAttributeCommand.setId(UUID.fromString(templateAttributeId));
        ApiResponse<TemplateAttributeResponseData> updateTemplateAttributeResponse = productApplicationService.updateTemplateAttribute(modifyTemplateAttributeCommand);
        log.info("Successfully updated template attribute with id {}", templateAttributeId);
        return ResponseEntity.ok(updateTemplateAttributeResponse);
    }

    @DeleteMapping("/{templateAttributeId}")
    @PreAuthorize("hasAuthority('DELETE_TEMPLATE')")
    public ResponseEntity<ApiResponse<TemplateAttributeResponseData>> delete(@PathVariable @ValidUUID String templateAttributeId) {
        ApiResponse<TemplateAttributeResponseData> deleteTemplateAttributeResponse = productApplicationService.deleteTemplateAttribute(UUID.fromString(templateAttributeId));
        log.info("Successfully deleted template attribute with id {}", templateAttributeId);
        return ResponseEntity.ok(deleteTemplateAttributeResponse);
    }
}
