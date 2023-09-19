package org.neshan.apireportservice.entity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter

public class ExtraFieldsAccident implements Serializable {

    private boolean police;
    private boolean ambulance;
    private boolean fire;
    private String description;
}
