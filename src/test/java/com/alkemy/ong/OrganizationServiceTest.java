package com.alkemy.ong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.model.OrganizationModel;
import com.alkemy.ong.service.OrganizationService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class OrganizationServiceTest {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void testGetAll() {
        List<Organization> list = organizationService.findAll();

        assertEquals(2, list.size());
    }
}
