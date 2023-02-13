package com.example.socksshopapp.service;

import com.example.socksshopapp.model.Socks;

import java.util.TreeMap;

public abstract interface ShopService {

    final TreeMap<Integer, Socks> socksMap = new TreeMap<>();

    Socks addSocks(Socks socks);

    Socks editSocks(int id, Socks socks);
    TreeMap<Integer, Socks> getAllSocks();

    boolean deleteById(int id);
}
