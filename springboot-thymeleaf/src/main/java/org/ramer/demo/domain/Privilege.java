package org.ramer.demo.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by RAMER on 5/26/2017.
 */
@Entity
@Data
public class Privilege{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;

}
