package org.slit.slitp2.controller;

import lombok.RequiredArgsConstructor;
import org.slit.slitp2.persistance.ItemRepo;
import org.slit.slitp2.request.Appointment;
import org.slit.slitp2.request.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 10:37 PM
 */

@RestController
@RequestMapping("item")
@RequiredArgsConstructor
@Service
public class ItemController {

    private final ItemRepo itemRepo;

    @GetMapping("find")
    public Item find(@RequestParam Long id){
        return itemMapper(findById(id));
    }

    @PostMapping
    public String save(@RequestBody Item item){
        if (item.getName()==null || item.getName().isEmpty()){
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (item.getPrice()==null||item.getPrice()<=0){
            throw new IllegalArgumentException("Item price cannot be null or empty");
        }
        org.slit.slitp2.persistance.Item entity = new org.slit.slitp2.persistance.Item();
        entity.setName(item.getName());
        entity.setPrice(item.getPrice());
        itemRepo.save(entity);
        return "Saved";
    }

    @PutMapping
    public String update(@RequestBody Item item){
        if (item.getId()==null||item.getId()<=0) {
            throw new IllegalArgumentException("Item id cannot be null or empty");
        }
        org.slit.slitp2.persistance.Item entity = findById(item.getId().longValue());

        if (!(item.getName()==null || item.getName().isEmpty())){
            entity.setName(item.getName());
        }
        if (!(item.getPrice()==null||item.getPrice()<=0)){
            entity.setPrice(item.getPrice());
        }
        itemRepo.save(entity);
        return "updated";
    }

    @DeleteMapping
    public String delete(@RequestParam Long id){
        itemRepo.delete(findById(id));
        return "deleted";
    }

    @GetMapping
    public List<Item> findAll(){
        return itemRepo.findAll().stream().map(this::itemMapper).toList();
    }

    private Item itemMapper(org.slit.slitp2.persistance.Item item) {
        Item response = new Item();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setPrice(item.getPrice());
        return response;
    }

    private org.slit.slitp2.persistance.Item findById(Long id){
        return itemRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }
}
