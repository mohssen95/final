package org.neshan.apireportservice.entity.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.neshan.apireportservice.entity.model.enums.ReportType;
import org.postgis.Point;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "report")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    private Point geom;

    private long senderId;

    @CreationTimestamp
    private Timestamp timestamp;

    @Enumerated(EnumType.STRING)
    ReportType reportType;


    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> extra = new HashMap<>();


}
