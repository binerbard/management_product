package com.management.product.scripts.seeds;

import com.management.product.datasources.domain.entity.AdminEntity;
import com.management.product.datasources.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AdminSeeder {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminSeeder(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void seed() {
        try {
            if (adminRepository.count() == 0) {
                AdminEntity account = new AdminEntity();
                account.setRole(AdminEntity.Role.ACCOUNT);
                account.setPassword("!9Password");
                account.setUsername("account@email.com");
                adminRepository.save(account);

                AdminEntity admin = new AdminEntity();
                admin.setRole(AdminEntity.Role.ADMINISTRATOR);
                admin.setPassword("!91Password");
                admin.setUsername("admin@email.com");
                adminRepository.save(admin);
                log.info("Admin is added");
            } else {
                log.info("Admin is already exist");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
