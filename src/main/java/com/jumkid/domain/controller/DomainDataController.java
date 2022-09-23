package com.jumkid.domain.controller;

import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.service.DomainDataService;
import com.jumkid.share.controller.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/domaindata")
public class DomainDataController {

    private final DomainDataService domainDataService;

    @Autowired
    public DomainDataController(DomainDataService domainDataService) {
        this.domainDataService = domainDataService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('GUEST_ROLE', 'USER_ROLE', 'ADMIN_ROLE')")
    public DomainData getDomainData(@PathVariable @NotBlank Long id) {
        return domainDataService.getDomainData(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('GUEST_ROLE', 'USER_ROLE', 'ADMIN_ROLE')")
    public List<DomainData> getDomainData(@RequestParam @NotBlank String industry,
                                          @RequestParam @NotBlank String name) {
        return domainDataService.getDomainData(industry, name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public DomainData saveDomainData(@RequestParam @NotBlank String industry,
                                     @RequestParam @NotBlank String name,
                                     @RequestParam @NotBlank String value) {
        return domainDataService.saveDomainData(industry, name, value);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public DomainData updateDomainData(@PathVariable @NotBlank Long id,
                                       @RequestBody DomainData domainData) {
        return domainDataService.updateDomainData(id, domainData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public CommonResponse deleteDomainData(@PathVariable @NotBlank Long id){
        Boolean deleted = domainDataService.deleteDomainData(id);
        return CommonResponse.builder().success(deleted).build();
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public Integer importDomainData(@NotNull @RequestParam("file") MultipartFile file) {
        try {
            return domainDataService.importDomainData(file.getInputStream()).size();
        } catch (IOException e) {
            log.error("Not able to get csv input {}", e.getMessage());
        }
        return 0;
    }

}
