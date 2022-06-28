package br.com.alura.jdbc.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.alura.jdbc.controller.CategoriaController;
import br.com.alura.jdbc.controller.ProdutoController;
import br.com.alura.jdbc.model.Categoria;
import br.com.alura.jdbc.model.Produto;

public class ProdutoCategoriaFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel labelNome, labelDescricao, labelCategoria;
	private JTextField textoNome, textoDescricao;
	private JComboBox<Categoria> comboCategoria;
	private JButton botaoSalvar, botaoEditar, botaoLimpar, botarApagar;
	private JTable tabela;
	private DefaultTableModel modelo;
	private ProdutoController produtoController;
	private CategoriaController categoriaController;

	public ProdutoCategoriaFrame() {
		super("Produtos");
		Container container = getContentPane();
		setLayout(null);

		this.categoriaController = new CategoriaController();
		this.produtoController = new ProdutoController();

		labelNome = new JLabel("Nome do Produto");
		labelDescricao = new JLabel("Descrição do Produto");
		labelCategoria = new JLabel("Categoria do Produto");

		labelNome.setBounds(10, 10, 240, 15);
		labelDescricao.setBounds(10, 50, 240, 15);
		labelCategoria.setBounds(10, 90, 240, 15);

		labelNome.setForeground(Color.BLACK);
		labelDescricao.setForeground(Color.BLACK);
		labelCategoria.setForeground(Color.BLACK);

		container.add(labelNome);
		container.add(labelDescricao);
		container.add(labelCategoria);

		textoNome = new JTextField();
		textoDescricao = new JTextField();
		comboCategoria = new JComboBox<Categoria>();

		comboCategoria.addItem(new Categoria(0, "Selecione"));
		List<Categoria> categorias = this.listarCategoria();
		for (Categoria categoria : categorias) {
			comboCategoria.addItem(categoria);
		}

		textoNome.setBounds(10, 25, 400, 20);
		textoDescricao.setBounds(10, 65, 400, 20);
		comboCategoria.setBounds(10, 105, 760, 20);

		container.add(textoNome);
		container.add(textoDescricao);
		container.add(comboCategoria);

		botaoSalvar = new JButton("Salvar");
		botaoLimpar = new JButton("Limpar");

		botaoSalvar.setBounds(10, 145, 190, 20);
		botaoLimpar.setBounds(210, 145, 200, 20);

		container.add(botaoSalvar);
		container.add(botaoLimpar);

		tabela = new JTable();
		modelo = (DefaultTableModel) tabela.getModel();

		modelo.addColumn("Identificador do Produto");
		modelo.addColumn("Nome do Produto");
		modelo.addColumn("Descrição do Produto");

		preencherTabela();

		tabela.setBounds(10, 185, 760, 300);
		container.add(tabela);

		botarApagar = new JButton("Excluir");
		botaoEditar = new JButton("Alterar");

		botarApagar.setBounds(10, 500, 80, 20);
		botaoEditar.setBounds(100, 500, 80, 20);

		container.add(botarApagar);
		container.add(botaoEditar);

		setSize(300, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		
		//Rebounding just to get it right on first draw.
		int width = getWidth() - 40;
		int height = getHeight();
		textoNome.setBounds(10, 25, width, 20);
		textoDescricao.setBounds(10, 65, width, 20);
		comboCategoria.setBounds(10, 105, width, 20);
		botaoSalvar.setBounds(10, 142, width/2 - 20, 25);
		botaoLimpar.setBounds(width/2, 142, width/2 + 10, 25);
		tabela.setBounds(10, 185, width, height-265);
		botarApagar.setBounds(10, height-75, width/2 - 20, 25);
		botaoEditar.setBounds(width/2, height-75, width/2 + 10, 25);

		botaoSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvar();
				limparTabela();
				preencherTabela();
			}
		});

		botaoLimpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});

		botarApagar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletar();
				limparTabela();
				preencherTabela();
			}
		});

		botaoEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterar();
				limparTabela();
				preencherTabela();
			}
		});
		super.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				int width = e.getComponent().getWidth() - 40;
				int height = e.getComponent().getHeight();
				textoNome.setBounds(10, 25, width, 20);
				textoDescricao.setBounds(10, 65, width, 20);
				comboCategoria.setBounds(10, 105, width, 20);
				botaoSalvar.setBounds(10, 142, width/2 - 20, 25);
				botaoLimpar.setBounds(width/2, 142, width/2 + 10, 25);
				tabela.setBounds(10, 185, width, height-265);
				botarApagar.setBounds(10, height-75, width/2 - 20, 25);
				botaoEditar.setBounds(width/2, height-75, width/2 + 10, 25);
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}  

	private void limparTabela() {
		modelo.getDataVector().clear();
		textoNome.setBounds(10, 25, getSize().width, 20);
	}

	private void alterar() {
		Object objetoDaLinha = (Object) modelo.getValueAt(tabela.getSelectedRow(), tabela.getSelectedColumn());
		if (objetoDaLinha instanceof Integer) {
			Integer id = (Integer) objetoDaLinha;
			String nome = (String) modelo.getValueAt(tabela.getSelectedRow(), 1);
			String descricao = (String) modelo.getValueAt(tabela.getSelectedRow(), 2);
			this.produtoController.alterar(nome, descricao, id);
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
		}
	}

	private void deletar() {
		Object objetoDaLinha = (Object) modelo.getValueAt(tabela.getSelectedRow(), tabela.getSelectedColumn());
		if (objetoDaLinha instanceof Integer) {
			Integer id = (Integer) objetoDaLinha;
			this.produtoController.deletar(id);
			modelo.removeRow(tabela.getSelectedRow());
			JOptionPane.showMessageDialog(this, "Item excluído com sucesso!");
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
		}
	}

	private void preencherTabela() {
		List<Produto> produtos = listarProduto();
		try {
			for (Produto produto : produtos) {
				modelo.addRow(new Object[] { produto.getId(), produto.getNome(), produto.getDescricao() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private List<Categoria> listarCategoria() {
		return this.categoriaController.listar();
	}

	private void salvar() {
		if (!textoNome.getText().equals("") && !textoDescricao.getText().equals("")) {
			Produto produto = new Produto(textoNome.getText(), textoDescricao.getText());
			Categoria categoria = (Categoria) comboCategoria.getSelectedItem();
			produto.setCategoriaId(categoria.getId());
			this.produtoController.salvar(produto);
			JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
			this.limpar();
		} else {
			JOptionPane.showMessageDialog(this, "Nome e Descrição devem ser informados.");
		}
	}

	private List<Produto> listarProduto() {
		return this.produtoController.listar();
	}

	private void limpar() {
		this.textoNome.setText("");
		this.textoDescricao.setText("");
		this.comboCategoria.setSelectedIndex(0);
	}
}
