package benchmark.hibernate;

public class HibernateDog {

	protected Long idDog;
	protected String name;
	protected int age;

	public HibernateDog() {
		super();
	}

	public HibernateDog(final Long idDog, final String name, final int age) {
		super();
		this.idDog = idDog;
		this.name = name;
		this.age = age;
	}

	public Long getIdDog() {
		return idDog;
	}

	public void setIdDog(final Long idDog) {
		this.idDog = idDog;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "HibernateDog [idDog=" + idDog + ", name=" + name + ", age=" + age + "]";
	}

}
