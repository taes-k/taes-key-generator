package com.taes.key.generator.api.repository;

import com.taes.key.generator.api.entity.KeyNumberGeneric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyNumberGenericRepository extends JpaRepository<KeyNumberGeneric, KeyNumberGeneric.PrimaryKey>
{
}
