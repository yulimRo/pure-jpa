package com.orm.pure.jpa.ex07.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/*
 * 상속관계의 하위객체
 */

@Entity
@DiscriminatorValue("A") //구분타입값, INSERT 할때 DTYPE 칼럼에 해당 값 넣어줌
@PrimaryKeyJoinColumn(name="album_id") //ID 재정의
public class Album extends Item {
	private String artist;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	

}
