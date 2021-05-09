package com.taes.key.generator.api.repository;

import com.taes.key.generator.api.entity.KeySet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeySetRepository extends JpaRepository<KeySet, String>
{
    boolean existsByKeyId(String keyId);
}
