package com.example.exam_accounting.repository;

import com.example.exam_accounting.entity.User;
import com.example.exam_accounting.projection.IUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        SELECT 
            email as email, 
            full_name as fullName, 
            role as role, 
            status as status 
        FROM user
        """, nativeQuery = true)
    List<IUserProjection> findAllUserProjections();
    Optional<User> findByEmail(String email);
    User findById(long id);

}
