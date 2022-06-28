package br.com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.dao.ProdutoDAO;
import br.com.alura.jdbc.factory.ConnectionFactory;
import br.com.alura.jdbc.model.Produto;

public class ProdutoController {
	
	private ProdutoDAO produtoDAO;
	
	public ProdutoController() {
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.produtoDAO = new ProdutoDAO(connection);
	}

	public void deletar(Integer id) {
		System.out.println("Deletando produto");
		try {
			produtoDAO.deletar(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void salvar(Produto produto) {
		System.out.println("Salvando produto");
		try {
			produtoDAO.salvar(produto);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Produto> listar() {
		try {
			return produtoDAO.listar();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(String nome, String descricao, Integer id) {
		System.out.println("Alterando produto");
		try {
			produtoDAO.alterar(nome, descricao, id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
