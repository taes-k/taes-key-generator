package com.taes.key.generator.api.repository;

import com.taes.key.generator.api.entity.KeyString;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyStringRepository extends JpaRepository<KeyString, String>
{
}
