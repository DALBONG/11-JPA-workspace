package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.repository.ItemRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServise2 {

    private final ItemRepository2 itemRepository2;

    @Transactional
    public void saveItem(Item item){
        itemRepository2.save(item);
    }

    public Item findOne(Long itemId){
       return itemRepository2.findOne(itemId);
    }

    public List<Item> findAll(){
        return itemRepository2.findAll();
    }







}
