package com.taes.key.generator.api.controller;

import com.taes.key.generator.api.dto.KeySetDto;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.service.KeyService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RequestMapping("/api/key")
@RestController
public class KeyController
{
    private final KeyService keyService;
    private final ModelMapper modelMapper;

    @Autowired
    public KeyController(
        KeyService keyService
        , ModelMapper modelMapper)
    {
        this.keyService = keyService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public void registKeySet(@Valid @RequestBody KeySetDto.CreateReq keysetDto)
    {
        keyService.registKeySet(convertToEntity(keysetDto));
    }

    private KeySet convertToEntity(KeySetDto.CreateReq keysetDto)
    {
        KeySet keySet = modelMapper.typeMap(KeySetDto.CreateReq.class, KeySet.class)
            .addMappings(mapper -> mapper.map(KeySetDto.CreateReq::getKey, KeySet::setKeyId))
            .addMappings(mapper -> mapper.map(KeySetDto.CreateReq::getType, KeySet::setKeyType))
            .addMappings(mapper -> mapper.map(KeySetDto.CreateReq::getGenerator, KeySet::setKeyGenerator))
            .map(keysetDto);

        log.debug("entity : {}", keySet);
        return keySet;
    }
}
