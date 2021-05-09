package com.taes.key.generator.api.repository;

import com.taes.key.generator.api.entity.KeySet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeySetRepository extends JpaRepository<KeySet, String>
{

}
