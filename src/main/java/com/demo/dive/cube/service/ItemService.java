package com.demo.dive.cube.service;

import com.demo.dive.cube.model.Item;
import com.demo.dive.cube.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public void save(Item item){
        if(item != null){
            if(item.getId() == null){
                itemRepository.save(item);
            }
            else{
                Item itemExist = findOne(item.getId());
                if(itemExist != null){
                    itemExist = item;
                    itemRepository.save(itemExist);
                }
            }
        }
    }

    public List<Item> findAll(){
        return itemRepository.findAllByIsDeletedFalse();
    }

    public void delete(Long id){
        Item item = findOne(id);
        if(item != null){
            item.setIsDeleted(true);
            itemRepository.save(item);
        }
    }

    public String generateItemId() {
        Long id= itemRepository.getHighestId();
        if(id!=null)
            return "IT-"+id;
        else
            return "IT-01";

    }

    public Item findOne(Long id){
        return itemRepository.findOneByIdAndIsDeletedFalse(id);
    }


}
