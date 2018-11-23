package edu.splash.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Basic implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

   
    public Basic() {
    }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
