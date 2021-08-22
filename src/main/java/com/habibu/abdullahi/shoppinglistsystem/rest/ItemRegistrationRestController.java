package com.habibu.abdullahi.shoppinglistsystem.rest;

import java.util.List;


import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.habibu.abdullahi.shoppinglistsystem.dto.ItemDTO;
import com.habibu.abdullahi.shoppinglistsystem.exception.CustomErrorTypeItem;
import com.habibu.abdullahi.shoppinglistsystem.repository.ItemJpaRepository;


@RestController
@RequestMapping("/api/item")
public class ItemRegistrationRestController {
    
    public static final Logger logger = LoggerFactory.getLogger(ItemRegistrationRestController.class);
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    public void setItemJpaRespository(ItemJpaRepository itemJpaRepository) {
        this.itemJpaRepository = itemJpaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<ItemDTO>> listAllItems() {
        logger.info("Fetching all items");
        List<ItemDTO> items = itemJpaRepository.findAll();
        if (items.isEmpty()) {
            return new ResponseEntity<List<ItemDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ItemDTO>>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable("id") final Long id) {
        logger.info("Fetching item with id {}", id);
        ItemDTO item = itemJpaRepository.findById(id);
        if (item == null) {
            logger.error("Item with id {} not found",id);
            return new ResponseEntity<ItemDTO>(new CustomErrorTypeItem("User with id "+ id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ItemDTO>(item, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody final ItemDTO item) {
        logger.info("Creating new item: {}", item);
        if (itemJpaRepository.findByProductname(item.getProductname()) != null) {
            logger.error("Unable to create. Item with name {} already exist", item.getProductname());
            return new ResponseEntity<>(new CustomErrorTypeItem("Unable to create. Item with name " + item.getProductname() + " already exist"), HttpStatus.CONFLICT);
        }
        itemJpaRepository.save(item);
        return new ResponseEntity<ItemDTO>(HttpStatus.CREATED);

    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<ItemDTO> updateItem(@PathVariable("id") final Long id, @RequestBody ItemDTO item){
        logger.info("Updating item with id {}", id);
        ItemDTO currentItem = itemJpaRepository.findById(id);
        if (currentItem == null) {
            logger.error("Unable to update. Item with id {} not found", id);
            return new ResponseEntity<ItemDTO>(new CustomErrorTypeItem("Unable to update. Item with "+ id + " not found"), HttpStatus.NOT_FOUND);
        }
        currentItem.setProductname(item.getProductname());
        currentItem.setPrice(item.getPrice());
        currentItem.setQuantity(item.getQuantity());

        itemJpaRepository.saveAndFlush(currentItem);
        return new ResponseEntity<ItemDTO>(currentItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable("id") final Long id) {
        logger.info("Deleting item with id {}", id);
        ItemDTO item = itemJpaRepository.findById(id);

        if (item == null) {

            logger.error("Unable to delete. Item with {} not found", id);
            return new ResponseEntity<>(new CustomErrorTypeItem("Unable to delete. Item with id " + id + "not founf"),HttpStatus.NOT_FOUND);
    
        }
        itemJpaRepository.delete(item);
        return new ResponseEntity<>(new CustomErrorTypeItem("Deleted item with id " + id + "."), HttpStatus.NO_CONTENT);
    }
}
