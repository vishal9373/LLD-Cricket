package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Seat extends BaseModel{

    public String name;

    public SeatType seatType;

    @ManyToOne
    public Stadium stadium;

}
