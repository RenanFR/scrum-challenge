package com.scrum.challenge.codec;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;

import com.scrum.challenge.model.Hero;
import com.scrum.challenge.model.Skills;

public class HeroCodec implements CollectibleCodec<Hero>	{
	
	private Codec<Document> codec;
	
	public HeroCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Hero hero, EncoderContext encoderContext) {
		Document heroDocument = new Document();
		ObjectId objectId = hero.getObjectId();
		heroDocument.append("_id", objectId);
		String name = hero.getName();
		heroDocument.append("name", name);
		String password = hero.getPassword();
		heroDocument.append("password", password);
		BigDecimal xp = hero.getXp() != null? hero.getXp() : BigDecimal.valueOf(0);
		heroDocument.append("xp", xp.toString());
		Collection<? extends GrantedAuthority> authorities = hero.getAuthorities();
		heroDocument.append("classes", authorities);
		List<Skills> skills = hero.getSkills();
		if (skills != null) {
			List<Document> documentSkill = new ArrayList<>();
			for (Skills skill : skills) {
				documentSkill.add(new Document("title", skill.getDescription()));
			}
			heroDocument.append("skills", documentSkill);
		}
		codec.encode(writer, heroDocument, encoderContext);
	}

	@Override
	public Class<Hero> getEncoderClass() {
		return Hero.class;
	}

	@Override
	public Hero decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);
		String name = document.getString("name");
		String password = document.getString("password");
		Double xp = Double.valueOf(document.getString("xp"));
		ObjectId objectId = document.getObjectId("_id");
		Hero hero = new Hero();
		hero.setName(name);
		if (xp != null) {
			hero.setXp(new BigDecimal(xp));
		}
		@SuppressWarnings("unchecked")
		List<Skills> skills = (List<Skills>)document.get("skills", List.class);
		if (skills != null) {
			hero.setSkills(skills);
		}
		hero.setObjectId(objectId);
		hero.setPassword(password);
		return hero;
		
	}

	@Override
	public Hero generateIdIfAbsentFromDocument(Hero hero) {
		return documentHasId(hero)? 
				hero : 
					hero.generateId();
	}

	@Override
	public boolean documentHasId(Hero hero) {
		return hero.getObjectId() == null;
	}

	@Override
	public BsonValue getDocumentId(Hero hero) {
		 if (documentHasId(hero)) {
			return new BsonString(hero.getObjectId().toHexString()); 
		 } else {
			 throw new IllegalStateException();
		 }
	}	

}
