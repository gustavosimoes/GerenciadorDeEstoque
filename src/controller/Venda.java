/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;

/**
 *
 * @author gustavo
 */
public class Venda {

    private int idVenda;
    private double valorVenda;
    private java.sql.Date dataVenda;

    public int getIdVenda() {
        return idVenda;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public java.sql.Date getDataVenda() {
        return dataVenda;
    }

    public Venda(){
        
    }
    
    public Venda(double valorVenda, java.sql.Date dataVenda) {
        this.valorVenda = valorVenda;
        this.dataVenda = dataVenda;
    }

    public Venda(int idVenda, double valorVenda, java.sql.Date dataVenda) {
        this.idVenda = idVenda;
        this.valorVenda = valorVenda;
        this.dataVenda = dataVenda;
    }

}
