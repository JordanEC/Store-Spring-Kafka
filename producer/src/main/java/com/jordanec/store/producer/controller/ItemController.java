package com.jordanec.store.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jordanec.store.producer.entity.Item;
import com.jordanec.store.producer.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
@CrossOrigin(origins={"*"},allowedHeaders = {"*"})
@Slf4j
public class ItemController
{
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/item", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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


    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> update(@PathVariable("itemId") long itemId, @RequestBody Item item) throws JsonProcessingException
    {
        try
        {
            return new ResponseEntity<>(itemService.update(itemId, item), HttpStatus.OK);
        }
        catch (Exception e)
        {
            log.error("update() -> item: {}", item, e);
            return ResponseEntity.badRequest().build();
        }
    }


    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("itemId") long itemId) throws JsonProcessingException
    {
        try
        {
            itemService.delete(itemId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            log.error("delete() -> item: {}", itemId, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/item/bulkcreate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/item", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> findAll()
    {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }


    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> get(@PathVariable("itemId") long itemId)
    {
        return new ResponseEntity<>(itemService.findByItemId(itemId), HttpStatus.OK);
    }
}
