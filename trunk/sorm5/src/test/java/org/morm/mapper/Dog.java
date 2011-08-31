package org.morm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.morm.mapper.DataMapper;
import org.morm.mapper.IRowMapper;

import java.util.List;

public class Dog implements IRowMapper<Dog> {

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
		return DataMapper.queryUnique(dummy, "SELECT * FROM DOG WHERE ID_DOG=?", new Object[] { id });
	}

	public static List<Dog> loadAll() {
		return DataMapper.query(dummy, "SELECT * FROM DOG", new Object[] {});
	}

	public void insert() {
		DataMapper.update("INSERT INTO DOG (ID_DOG,NAME) VALUES (?,?)",
				new Object[] { getIdDog(), getName() });
	}

	public void update() {
		DataMapper.update("UPDATE DOG SET NAME=? WHERE ID_DOG=?", new Object[] { getName(), getIdDog() });
	}

	public void setChilds(final List<Dog> childs) {

	}

	public List<Dog> getChilds() {
		return null;
	}

}
