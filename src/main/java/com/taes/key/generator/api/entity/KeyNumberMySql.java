package com.taes.key.generator.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Entity
@Table(name = "key_number_mysql")
public class KeyNumberMySql
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

        @Column(name = "key_value", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long keyValue;

        public PrimaryKey(KeySet keySet)
        {
            this.keySet = keySet;
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

    public KeyNumberMySql(KeySet keySet)
    {
        this.primaryKey = new PrimaryKey(keySet);
    }
}
