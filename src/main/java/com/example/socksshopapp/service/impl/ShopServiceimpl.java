package com.example.socksshopapp.service.impl;

import com.example.socksshopapp.exception.InvalidFileException;
import com.example.socksshopapp.exception.InvalidSockException;
import com.example.socksshopapp.model.Socks;
import com.example.socksshopapp.service.FileService;
import com.example.socksshopapp.service.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TreeMap;

@Service
public class ShopServiceimpl implements ShopService {
    private static int id = 0;

    private final FileService fileService;

    public ShopServiceimpl(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    private static TreeMap<Integer, Socks> socksMap = new TreeMap<>();


    public Socks addSocks(Socks socks) {
        socksMap.put(id, socks);
        validateRequest(socks);
        saveToFile();
        id++;
        return socks;
    }

    @Override
    public Socks editSocks(int id, Socks socks) throws InvalidSockException {
        if (socksMap.containsKey(id)) {
            socksMap.put(id, socks);
            saveToFile();
            return socks;
        }
       throw new InvalidSockException("Товар не найден");
    }

    @Override
    public TreeMap<Integer, Socks> getAllSocks() {
        return socksMap;
    }


    private void validateRequest(Socks socks) {
        if (socks.getColour() == null || socks.getSize() == null) {
            throw new InvalidSockException("Заполните все поля");
        }
        if (socks.getCottonPart() < 0 && socks.getCottonPart() > 100) {
            throw new InvalidSockException("Неправильное содержание хлопка");
        }
    }

    @Override
    public boolean deleteById(int id) {
        if (socksMap.containsKey(id)) {
            socksMap.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new InvalidFileException("Неверный файл");
        }
    }

    private void readFromFile() {
        String json = fileService.readFromFile();
        try {
            socksMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Socks>>() {
            });
        } catch (JsonProcessingException e) {
            throw new InvalidFileException("Неверный файл");
        }
    }
}
