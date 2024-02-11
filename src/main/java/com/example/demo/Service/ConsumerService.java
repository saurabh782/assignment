package com.example.demo.Service;

import com.example.demo.Entity.ConsumerEntity;
import com.example.demo.Entry.ConsumerEntry;
import com.example.demo.Repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerRepository repository;

    @Autowired
    private LocationService locationService;

    public ConsumerEntry findConsumer(Long consumerId) {
        ConsumerEntity entity = repository.findById(consumerId);
        return convertToEntry(entity);
    }

    private ConsumerEntry convertToEntry(ConsumerEntity entity) {
        ConsumerEntry entry = new ConsumerEntry();
        entry.setName(entity.getName());
        entry.setUserId(entity.getUserId());
        entry.setLocationEntry(locationService.convertToEntry(entity.getLocationEntity()));
        return entry;
    }
}
