package com.taes.key.generator.api.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@NoArgsConstructor
@Setter
@Entity
@Table(name = "key_number_generic_sequence")
public class KeyNumberGenericSequence
{
    @Id
    @Column(name = "key_set_seq")
    private Integer keySetSeq;

    @Column(name = "next_val")
    private Long nextVal;

    @Version
    private int version;

    public Long getNextVal()
    {
        return nextVal;
    }

    public void addNextVal()
    {
        nextVal++;
    }
}
