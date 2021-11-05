package de.vs.searchms.database.entity;


import javax.persistence.*;

/**
 * This class contains details about products.
 */
@Entity
@Table(name = "product")
public class ProductEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "details")
	private String details;

	public ProductEntity() {
	}

	public ProductEntity(String name, double price, Category category) {
		this.name = name;
		this.price = price;
		this.category = category;
	}

	public ProductEntity(String name, double price, Category category, String details) {
		this.name = name;
		this.price = price;
		this.category = category;
		this.details = details;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", price=" + price +
				", category=" + category +
				", details='" + details + '\'' +
				'}';
	}
}
