package com.taes.key.generator.api.repository;

import com.taes.key.generator.api.entity.KeyNumberGenericSequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyNumberGenericSequenceRepository extends JpaRepository<KeyNumberGenericSequence, Integer>
{
}
