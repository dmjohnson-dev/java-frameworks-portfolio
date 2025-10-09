package com.example.demo.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class InhousePart extends Part {

    private Integer partId;

    public Integer getPartId() {
        return partId;
    }
    public void setPartId(Integer partId) {
        this.partId = partId;
    }
}
