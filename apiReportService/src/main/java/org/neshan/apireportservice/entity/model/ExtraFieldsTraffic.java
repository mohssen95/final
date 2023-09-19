package org.neshan.apireportservice.entity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@AllArgsConstructor
@Getter
@Setter
public class ExtraFieldsTraffic implements Serializable {

    private boolean isPoliceCame;
    private String description;

}
