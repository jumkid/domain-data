package com.jumkid.domain.controller;

import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.service.DomainDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DomainData> getDomainData(@RequestParam @NotBlank String industry,
                                          @RequestParam @NotBlank String name) {
        return domainDataService.getDomainData(industry, name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public DomainData saveDomainData(@RequestBody DomainData domainData) {
        return domainDataService.saveDomainData(domainData);
    }

    @DeleteMapping("{domainId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDomainData(@PathVariable @NotBlank Long domainId){
        domainDataService.deleteDomainData(domainId);
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public Integer importDomainData(@NotNull @RequestParam("file") MultipartFile file) {
        try {
            return domainDataService.importDomainData(file.getInputStream()).size();
        } catch (IOException e) {
            log.error("Not able to get csv input {}", e.getMessage());
        }
        return 0;
    }

}
