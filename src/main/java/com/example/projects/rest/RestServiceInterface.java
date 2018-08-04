package com.example.projects.rest;

import com.example.projects.dto.DtoInterface;

import java.util.List;

public interface RestServiceInterface {

    List<? extends DtoInterface> listAll();

    DtoInterface findById(Long id);
}
