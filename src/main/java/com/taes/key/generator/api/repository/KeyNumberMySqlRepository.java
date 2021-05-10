package com.taes.key.generator.api.repository;

import com.taes.key.generator.api.entity.KeyNumberMySql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeyNumberMySqlRepository extends JpaRepository<KeyNumberMySql, KeyNumberMySql.PrimaryKey>
{
    @Query(value = "SELECT last_insert_id()", nativeQuery=true)
    Long findLastInsertId();
}
