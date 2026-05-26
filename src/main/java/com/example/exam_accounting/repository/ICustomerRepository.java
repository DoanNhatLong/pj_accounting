package com.example.exam_accounting.repository;

import com.example.exam_accounting.entity.Customer;
import com.example.exam_accounting.projection.ICustomerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = """
        SELECT 
                    id as id,
            company_name as companyName, 
            tax_code as taxCode, 
            status as status 
        FROM customer
        """, nativeQuery = true)
    List<ICustomerProjection> findAllCustomerProjections();
    Customer findById(long id);
}
