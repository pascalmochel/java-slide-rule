package org.morm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.record.Entity;
import org.morm.record.QueryObject;

import java.util.List;

public class Dog extends Entity implements IRowMapper<Dog> {

	protected Integer idDog;
	protected String name;

	public Dog() {
		super();
	}

	public Dog(final Integer idDog, final String name) {
		super();
		this.idDog = idDog;
		this.name = name;
	}

	public Integer getIdDog() {
		return idDog;
	}

	public void setIdDog(final Integer idDog) {
		this.idDog = idDog;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Dog:(" + idDog + "," + name + ")";
	}

	public Dog mapRow(final ResultSet rs) throws SQLException {
		final Dog r = new Dog();
		r.setIdDog((Integer) rs.getObject("ID_DOG"));
		r.setName((String) rs.getObject("NAME"));
		return r;
	}

	public static Dog dummy = new Dog();

	public static Dog loadById(final Integer id) {
		return DataMapper.queryUnique(dummy, new QueryObject("SELECT * FROM DOG WHERE ID_DOG=?", id));
	}

	public static List<Dog> loadAll() {
		return DataMapper.query(dummy, new QueryObject("SELECT * FROM DOG"));
	}

	@Override
	public void insert() {
		DataMapper
				.update(new QueryObject("INSERT INTO DOG (ID_DOG,NAME) VALUES (?,?)", getIdDog(), getName()));
	}

	@Override
	public void update() {
		DataMapper.update(new QueryObject("UPDATE DOG SET NAME=? WHERE ID_DOG=?", getName(), getIdDog()));
	}

	public void setChilds(final List<Dog> childs) {

	}

	public List<Dog> getChilds() {
		return null;
	}

}
