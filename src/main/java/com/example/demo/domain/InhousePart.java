package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@DiscriminatorValue("1")
public class InhousePart extends Part {

    @Min(value = 0, message = "Part ID must be ≥ 0")
    @Column(name = "part_id")
    private Integer partId;

    public Integer getPartId() { return partId; }
    public void setPartId(Integer partId) { this.partId = partId; }
}

