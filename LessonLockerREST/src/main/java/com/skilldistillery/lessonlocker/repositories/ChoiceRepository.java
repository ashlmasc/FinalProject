package com.skilldistillery.lessonlocker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.lessonlocker.entities.Choice;

public interface ChoiceRepository extends JpaRepository <Choice, Integer> {

}
