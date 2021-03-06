package com.scrum.challenge.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;

import com.scrum.challenge.model.Hero;

public interface HeroDAO {
	
	Hero save(Hero hero);

	List<Hero> findAll();
	
	Hero findById(ObjectId id);
	
	void delete(Hero hero);

	UserDetails findByName(String username);
}
