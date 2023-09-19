package org.neshan.apireportservice.entity.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.neshan.apireportservice.entity.model.enums.ReportType;
import org.postgis.Point;

import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "report")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    private Point geom;

    private long senderId;

    @CreationTimestamp
    private Timestamp timestamp;

    /*
    * happen in n'th 2-min part of day
    * each day divided to 720 parts of 2min seq
    */
    private int timeSeq;

    @Enumerated(EnumType.STRING)
    ReportType reportType;



}
