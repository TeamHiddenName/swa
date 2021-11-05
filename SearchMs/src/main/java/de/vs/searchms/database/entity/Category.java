package de.vs.searchms.database.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains details about categories.
 */
@Entity
@Table(name = "category")
public class Category implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Set<ProductEntity> products = new HashSet<ProductEntity>(0);

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, Set<ProductEntity> products) {
		this.name = name;
		this.products = products;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	public Set<ProductEntity> getProducts() {
		return this.products;
	}

	public void setProducts(Set<ProductEntity> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
