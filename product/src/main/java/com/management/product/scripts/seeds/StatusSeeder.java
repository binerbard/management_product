package com.management.product.scripts.seeds;

import com.management.product.datasources.domain.entity.StatusEntity;
import com.management.product.datasources.repository.StatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StatusSeeder {
    private final StatusRepository  statusRepository;

    @Autowired
    public StatusSeeder(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    public void seed(){
        try {
            if (statusRepository.count() == 0) {
                StatusEntity statusApproved = new StatusEntity();
                statusApproved.setTitle("approve");
                StatusEntity statusPending = new StatusEntity();
                statusPending.setTitle("pending");
                StatusEntity statusRejected = new StatusEntity();
                statusRejected.setTitle("reject");
                statusRepository.saveAll(List.of(statusApproved,statusPending,statusRejected));
                log.info("Status is added");
            }else {
                log.info("Status is already exist");
            }
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
