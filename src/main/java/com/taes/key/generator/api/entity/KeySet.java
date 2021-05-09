package com.taes.key.generator.api.entity;

import com.taes.key.generator.api.enums.KeyGenerator;
import com.taes.key.generator.api.enums.KeyType;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Entity
@Table(name = "key_set")
public class KeySet
{
    @Id
    @Column(length = 300)
    private String keyId;
    @Column(length = 500)
    private String description;
    @Enumerated(EnumType.STRING)
    private KeyType keyType;
    @Enumerated(EnumType.STRING)
    private KeyGenerator keyGenerator;
    private Integer minLength;

    @CreationTimestamp
    @Column(name = "reg_dt", nullable = false, updatable = false, insertable = false)
    private LocalDateTime regDt;

    @UpdateTimestamp
    @Column(name = "chg_dt", nullable = false, insertable = false)
    private LocalDateTime chgDt;

    public String getKeyId()
    {
        return keyId;
    }

    @Override
    public String toString()
    {
        return "KeySet{" +
            "keyId='" + keyId + '\'' +
            ", description='" + description + '\'' +
            ", keyType=" + keyType +
            ", keyGenerator=" + keyGenerator +
            ", minLength=" + minLength +
            '}';
    }
}
