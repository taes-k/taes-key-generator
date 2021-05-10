package com.taes.key.generator.api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Entity
@Table(name = "key_string")
public class KeyString
{
    @EmbeddedId
    private PrimaryKey primaryKey;

    @CreationTimestamp
    @Column(name = "reg_dt", nullable = false, updatable = false, insertable = false)
    private LocalDateTime regDt;

    @NoArgsConstructor
    @Data
    @Embeddable
    public static class PrimaryKey implements Serializable
    {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "key_set_seq", insertable = false, updatable = false)
        private KeySet keySet;

        @Column(nullable = false)
        private String keyValue;

        public PrimaryKey(KeySet keySet, String keyValue)
        {
            this.keySet = keySet;
            this.keyValue = keyValue;
        }
    }

    public KeyString(KeySet keySet, String keyValue)
    {
        this.primaryKey = new PrimaryKey(keySet, keyValue);
    }
}
