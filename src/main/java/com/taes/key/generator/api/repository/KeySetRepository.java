package com.taes.key.generator.api.repository;

import com.taes.key.generator.api.entity.KeySet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeySetRepository extends JpaRepository<KeySet, String>
{
    Optional<KeySet> findByKeyId(String keyId);

    boolean existsByKeyId(String keyId);
}
