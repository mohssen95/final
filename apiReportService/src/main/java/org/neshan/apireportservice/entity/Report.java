package org.neshan.apireportservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;
import org.neshan.apireportservice.entity.model.enums.ReportType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Entity
@Table(name = "report")
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

    @OneToMany(mappedBy = "report")
    private Set<Interaction> interactions=new HashSet<>();

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> extra = new HashMap<>();


}
