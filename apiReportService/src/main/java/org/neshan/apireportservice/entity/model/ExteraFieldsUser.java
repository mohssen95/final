package org.neshan.apireportservice.entity.model;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

public class ExteraFieldsUser {

    //how much his report can trust
    @Column(name = "trustRate")
    private float trustRate;

    @CreationTimestamp
    private Timestamp timestamp;


}
