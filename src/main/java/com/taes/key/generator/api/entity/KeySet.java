package com.taes.key.generator.api.entity;

import com.taes.key.generator.api.enums.KeyGenerator;
import com.taes.key.generator.api.enums.KeyType;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Entity
@Table(name = "key_set", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"keyId"}
    )})
public class KeySet
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer keySetSeq;

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

    public Integer getKeySetSeq()
    {
        return keySetSeq;
    }

    public String getKeyId()
    {
        return keyId;
    }

    public KeyType getKeyType()
    {
        return keyType;
    }

    public KeyGenerator getGenerator()
    {
        return keyGenerator;
    }

    @Override
    public String toString()
    {
        return "KeySet{" +
            "keySeq=" + keySetSeq +
            ", keyId='" + keyId + '\'' +
            ", description='" + description + '\'' +
            ", keyType=" + keyType +
            ", keyGenerator=" + keyGenerator +
            ", minLength=" + minLength +
            ", regDt=" + regDt +
            ", chgDt=" + chgDt +
            '}';
    }
}
