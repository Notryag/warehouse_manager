package com.pn.service;

import com.pn.entity.Place;
import com.pn.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    @Autowired
    private PlaceMapper placeMapper;

    public List<Place> queryAllPlace() {
        return placeMapper.findAllPlace();
    }
}
