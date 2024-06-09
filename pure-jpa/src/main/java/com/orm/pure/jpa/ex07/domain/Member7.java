package com.orm.pure.jpa.ex07.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

//@Entity
public class Member7 {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long id;

	private String name;

	//값 타입을 하나 이상 저장하려면 컬렉션에 보관
	//기본값타입
	@ElementCollection
	@CollectionTable(name="FAVORITE_FOODS",joinColumns = @JoinColumn(name = "member_id"))
	@Column(name="FOOD_NAME") //값으로 사용되는 칼럼이 하나면 @Column 으로 매핑
	private Set<String> favoriteFoods = new HashSet<String>();

	//임베디드타입
	@ElementCollection
	@CollectionTable(name="ADDRESS", joinColumns = {@JoinColumn(name="member_id")})
	private List<Address> addressHistory = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="team_id")
	private Team7 team;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Order7> orders = new ArrayList<>();



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team7 getTeam() {
		return team;
	}

	public void setTeam(Team7 team) {
		this.team = team;
	}

	public List<Order7> getOrders() {
		return orders;
	}

	public void setOrders(List<Order7> orders) {
		this.orders = orders;
	}

	public Set<String> getFavoriteFoods() {
		return favoriteFoods;
	}

	public void setFavoriteFoods(Set<String> favoriteFoods) {
		this.favoriteFoods = favoriteFoods;
	}

	public List<Address> getAddressHistory() {
		return addressHistory;
	}

	public void setAddressHistory(List<Address> addressHistory) {
		this.addressHistory = addressHistory;
	}
}
