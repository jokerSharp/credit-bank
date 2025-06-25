package io.project.deal.service.impl;

import io.project.deal.data.DealTestData;
import io.project.deal.mapper.EmploymentMapper;
import io.project.deal.model.entity.Employment;
import io.project.deal.repository.EmploymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmploymentServiceImplTest {

    @Mock
    private EmploymentRepository employmentRepository;

    @Mock
    private EmploymentMapper employmentMapper;

    @InjectMocks
    private EmploymentServiceImpl employmentService;

    @Test
    void saveEmployment_returnsSavedEmployment() {
        when(employmentMapper.toEntity(DealTestData.EMPLOYMENT_DTO_2))
                .thenReturn(DealTestData.UNSAVED_EMPLOYMENT_ENTITY_2);
        when(employmentRepository.save(DealTestData.UNSAVED_EMPLOYMENT_ENTITY_2))
                .thenReturn(DealTestData.SAVED_EMPLOYMENT_ENTITY_2);

        Employment result = employmentService.create(DealTestData.EMPLOYMENT_DTO_2);

        assertNotNull(result);
        assertEquals(DealTestData.SAVED_EMPLOYMENT_ENTITY_2, result);
        assertEquals(DealTestData.SAVED_EMPLOYMENT_ENTITY_2.getEmploymentId(), DealTestData.EMPLOYMENT_ID);
    }

}