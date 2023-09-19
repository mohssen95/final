package org.neshan.apireportservice.entity.report;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neshan.apireportservice.entity.model.enums.TrafficType;

import java.io.Serializable;

@Entity
@DiscriminatorValue("traffic")
@PrimaryKeyJoinColumn(name = "traffic_id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrafficReport extends Report implements Serializable {
    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private TrafficType trafficType;
    private int length;

//    @JdbcTypeCode(SqlTypes.JSON)
//    private ExtraFieldsTraffic extra;


}
