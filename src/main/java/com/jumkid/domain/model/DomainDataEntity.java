package com.jumkid.domain.model;

import lombok.*;

import jakarta.persistence.*;

@Table(name = "domain_industry")
@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DomainDataEntity {

    @Id
    @Column(name = "domain_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long domainId;

    @Column(name = "industry")
    private String industry;

    @Column(name = "domain_name")
    private String name;

    @Column(name = "domain_value")
    private String value;

}
