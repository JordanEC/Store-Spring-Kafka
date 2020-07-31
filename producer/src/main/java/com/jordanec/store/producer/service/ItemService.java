package com.jordanec.store.producer.service;

import com.jordanec.store.producer.entity.Item;
import com.jordanec.store.producer.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService
{
    @Autowired
    ItemRepository itemRepository;

    @Transactional
    public Item create(Item item)
    {
        itemRepository.save(item);
        return item;
    }

    @Transactional
    public List<Item> create(List<Item> items)
    {
        itemRepository.saveAll(items);
        return items;
    }

    public Item findByName(String name)
    {
        return itemRepository.findByName(name);
    }

    public Item findByItemId(Long itemId)
    {
        return itemRepository.findByItemId(itemId);
    }

    public List<Item> findAll()
    {
        return itemRepository.findAll();
    }
}
