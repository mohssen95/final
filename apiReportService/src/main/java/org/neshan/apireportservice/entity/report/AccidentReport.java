package org.neshan.apireportservice.entity.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neshan.apireportservice.entity.model.enums.AccidentType;

import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "accident_id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccidentReport extends Report implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "acc_type")
    private AccidentType accidentType;



//    @JdbcTypeCode(SqlTypes.JSON)
//    private ExtraFieldsTraffic extra;

}
