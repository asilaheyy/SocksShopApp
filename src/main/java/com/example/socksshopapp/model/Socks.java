package com.example.socksshopapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    private Size size;

    private Colour colour;

    private int cottonPart;

    private int id;
}
