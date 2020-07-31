package com.jordanec.store.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jordanec.store.producer.entity.Item;
import com.jordanec.store.producer.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api")
@Slf4j
public class ItemController
{
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/item/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> create(@RequestBody Item item) throws JsonProcessingException
    {
        try
        {
            return new ResponseEntity<>(itemService.create(item), HttpStatus.OK);
        }
        catch (Exception e)
        {
            log.error("create() -> item: {}", item, e);
            return ResponseEntity.badRequest().build();
        }
    }


    @RequestMapping(value = "/item/bulkcreate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> bulkCreate(@RequestBody List<Item> items) throws JsonProcessingException
    {
        try
        {
            return new ResponseEntity<>(itemService.create(items), HttpStatus.OK);
        }
        catch (Exception e)
        {
            log.error("bulkCreate() -> {}", items, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> findAll()
    {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }
}
