package com.plurasight.service;

import com.plurasight.model.Speaker;
import com.plurasight.repository.HibernateSpeakerRepositoryImpl;
import com.plurasight.repository.SpeakerRepository;

import java.util.List;

public class SpeakerServiceImpl implements SpeakerService {
    private final SpeakerRepository repository = new HibernateSpeakerRepositoryImpl();

    @Override
    public List<Speaker> findAll() {
        return repository.findAll();
    }
}
