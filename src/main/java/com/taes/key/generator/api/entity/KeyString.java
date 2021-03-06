package com.taes.key.generator.api.entity;

import lombok.Data;
import lombok.Getter;
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
import java.util.Objects;

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
    @Getter
    @Setter
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

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PrimaryKey that = (PrimaryKey) o;
            return Objects.equals(keySet, that.keySet) && Objects.equals(keyValue, that.keyValue);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(keySet, keyValue);
        }
    }

    public KeyString(KeySet keySet, String keyValue)
    {
        this.primaryKey = new PrimaryKey(keySet, keyValue);
    }
}
