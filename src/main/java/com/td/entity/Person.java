package com.td.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "person")
@Entity
public class Person {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // 使用数据库自增di
    // 以下策略在mysql中均为新建一张sequence表来记录ID
    //@GeneratedValue(strategy = GenerationType.TABLE)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    //private Integer id;

    // 使用UUID
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String name;
    private Integer age;
}
