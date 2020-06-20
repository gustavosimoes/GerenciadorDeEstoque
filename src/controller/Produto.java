/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 * @author gustavo
 */
public class Produto {

    private static int quantidadeEstoque = 0;
    private int codigo;
    private String nomeProduto;
    private String descricao;
    private double preco;

    public static int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public static void setQuantidadeEstoque(int quantidadeEstoque) {
        Produto.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Produto(int codigo, String nomeProduto, String descricao, double preco) {
        this.codigo = codigo;
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.preco = preco;
    }

    public void atualizaEstoqueInsercao() {
        Produto.setQuantidadeEstoque(Produto.getQuantidadeEstoque() + 1);
    }

    public void atualizaEstoqueVenda() {
        Produto.setQuantidadeEstoque(Produto.getQuantidadeEstoque() - 1);
    }

}
