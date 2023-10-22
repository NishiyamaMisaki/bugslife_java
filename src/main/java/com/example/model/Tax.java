package com.example.model;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "taxes")
@Getter
@Setter
public class Tax implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Column(name = "rate", nullable = true)
	public Integer rate;

	@Column(name = "tax_included", nullable = true)
	public Boolean tax_included;

	@Column(name = "rounding", nullable = true)
	public String rounding;

	public static Tax get(Integer id) {
		List<Tax> taxes = new ArrayList<Tax>();
		for (Tax tax : taxes) {
			if (tax.id.equals(id)) {
				return tax;
			}
		}
		return null;
	}

	public static Tax get(Integer rate, Boolean tax_included, String rounding) {
		List<Tax> taxes = new ArrayList<Tax>();
		for (Tax tax : taxes) {
			if (tax.rate.equals(rate) && tax.tax_included.equals(tax_included) && tax.rounding.equals(rounding)) {
				return tax;
			}
		}
		return null;
	}
}
