package com.baddingSystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baddingSystem.entities.Winner;
import com.baddingSystem.repository.WinnerRepository;

@Service
public class WinnerService {

    @Autowired
    private WinnerRepository winnerRepository;

    public List<Winner> getAllWinners() {
        return winnerRepository.findAllByOrderByDeclaredDateDesc();
    }
}

