package com.jumkid.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "domain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainDataEntity {

    @Id
    @Column(name = "domain_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long domainId;

    @Column(name = "industry")
    private String industry;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

}
