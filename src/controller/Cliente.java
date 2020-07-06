/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;

/**
 *
 * @author gustavo
 */
public class Cliente {
    private ArrayList<Venda> listaVendas = new ArrayList<>();
    private long cpf;
    private long telefone;
    private String nome;
    private String endereco;

    public Cliente(long cpf, long telefone, String nome, String endereco) {
        this.cpf = cpf;
        this.telefone = telefone;
        this.nome = nome;
        this.endereco = endereco;
    }

    public ArrayList<Venda> getListaVendas() {
        return listaVendas;
    }

    public long getCpf() {
        return cpf;
    }

    public long getTelefone() {
        return telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setListaVendas(ArrayList<Venda> listaVendas) {
        this.listaVendas = listaVendas;
    }
    
    public double getSaldoDevedor(){
        double saldoDevedor = 0;
        for(Venda venda : this.listaVendas){
            saldoDevedor += venda.getValorVenda();
        }
        return saldoDevedor;
    }
}
