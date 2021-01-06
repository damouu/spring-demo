package com.plurasight.repository;

import com.plurasight.model.Speaker;

import java.util.ArrayList;
import java.util.List;

public class HibernateSpeakerRepositoryImpl implements SpeakerRepository {

    @Override
    public List<Speaker> findAll() {
        List<Speaker> speakers = new ArrayList<>();
        Speaker speaker = new Speaker();
        speaker.setFirstName("Bilal");
        speaker.setFirstName("Lagrini");

        speakers.add(speaker);

        return speakers;
    }
}
