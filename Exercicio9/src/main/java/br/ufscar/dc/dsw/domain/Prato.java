package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Prato")
public class Prato extends AbstractEntity<Long> {

	@NotBlank(message = "{NotBlank.prato.titulo}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String nome;

	@NotBlank(message = "{NotBlank.prato.autor}")
	@Size(max = 60)
	@Column(nullable = false, length = 512)
	private String descricao;
	
	@NotNull(message = "{NotNull.prato.preco}")
	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal preco;
    
	@NotNull(message = "{NotNull.prato.restaurante}")
	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
}
