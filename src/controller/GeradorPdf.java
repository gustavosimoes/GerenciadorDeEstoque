/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.ProdutoDAO;
import model.VendaDAO;

/**
 *
 * @author gustavo
 */
public class GeradorPdf {

    
    /**
     * Esta função salva o Pdf do Estoque no diretório desejado.
     */
    
    public boolean salvarPdfEstoque() throws FileNotFoundException, DocumentException, IOException {

        boolean sucesso = false;

        ProdutoDAO daoProduto = new ProdutoDAO();

        Document document = new Document();
        String fileDirectory = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecionar a Pasta...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(new JPanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            fileDirectory = fileChooser.getSelectedFile().getPath();

            try {

                PdfWriter.getInstance(document, new FileOutputStream(fileDirectory + "/Estoque.pdf"));
                document.open();
                Paragraph pg = new Paragraph(0, "Estoque\n\n\n");
                document.add(pg);
                ArrayList<Produto> listaProdutos = daoProduto.getProdutos();
                if (!listaProdutos.isEmpty()) {
                    document.add(this.inserirTabelaEstoque(listaProdutos));
                } else {
                    document.add(new Paragraph("Estoque Vazio."));
                }
                sucesso = true;
            } catch (DocumentException | FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            } finally {
                document.close();
            }
            Desktop.getDesktop().open(new File(fileDirectory + "/Estoque.pdf"));
        }

        return sucesso;
    }

    /**
     * Esta função retorna a tabela de produtos a ser inserida no pdf do estoque.
     */
    private PdfPTable inserirTabelaEstoque(ArrayList<Produto> listaProdutos) throws DocumentException {

        //CRIANDO CABEÇALHO
        PdfPTable table = new PdfPTable(new float[]{5f, 5f, 8f, 5f, 5f, 5f});

        PdfPCell celulaCodigo = new PdfPCell(new Phrase("Código"));
        celulaCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell celulaNome = new PdfPCell(new Phrase("Nome"));
        celulaNome.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell celulaDescricao = new PdfPCell(new Phrase("Descrição"));
        celulaDescricao.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell celulaPrecoDeCusto = new PdfPCell(new Phrase("Preço de Custo"));
        celulaPrecoDeCusto.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell celulaPreco = new PdfPCell(new Phrase("Preco"));
        celulaPreco.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell celulaQtdEstoque = new PdfPCell(new Phrase("Qtd Estoque"));
        celulaQtdEstoque.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(celulaCodigo);
        table.addCell(celulaNome);
        table.addCell(celulaDescricao);
        table.addCell(celulaPrecoDeCusto);
        table.addCell(celulaPreco);
        table.addCell(celulaQtdEstoque);

        for (Produto produto : listaProdutos) {
            PdfPCell celula1 = new PdfPCell(new Phrase(Long.toString(produto.getCodigo())));
            PdfPCell celula2 = new PdfPCell(new Phrase(produto.getNomeProduto()));
            PdfPCell celula3 = new PdfPCell(new Phrase(produto.getDescricao()));
            PdfPCell celula4 = new PdfPCell(new Phrase(Double.toString(produto.getPrecoDeCusto())));
            PdfPCell celula5 = new PdfPCell(new Phrase(Double.toString(produto.getPreco())));
            PdfPCell celula6 = new PdfPCell(new Phrase(Integer.toString(produto.getQuantidadeEstoque())));

            celula1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celula2.setHorizontalAlignment(Element.ALIGN_CENTER);
            celula3.setHorizontalAlignment(Element.ALIGN_CENTER);
            celula4.setHorizontalAlignment(Element.ALIGN_CENTER);
            celula5.setHorizontalAlignment(Element.ALIGN_CENTER);
            celula6.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(celula1);
            table.addCell(celula2);
            table.addCell(celula3);
            table.addCell(celula4);
            table.addCell(celula5);
            table.addCell(celula6);
        }
        return table;
    }

    /**
     * Esta função salva o Pdf de Vendas Diárias no diretório desejado.
     */
    
    public void salvarPdfVendaDiaria(Date dataVenda) throws IOException {

        VendaDAO daoVenda = new VendaDAO();
        ArrayList<Venda> listaVendas = new ArrayList<>();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date dataVendaParam = new java.sql.Date(dataVenda.getTime());

        Document document = new Document();
        String fileDirectory = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecionar a Pasta...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(new JPanel());
        String nomeArquivo = "/Vendas" + f.format(dataVenda).replace("/", "") + ".pdf";

        if (result == JFileChooser.APPROVE_OPTION) {
            fileDirectory = fileChooser.getSelectedFile().getPath();

            try {

                PdfWriter.getInstance(document, new FileOutputStream(fileDirectory + nomeArquivo));
                document.open();
                document.add(new Paragraph("Vendas"));
                Paragraph pgdata = new Paragraph(f.format(dataVenda) + "\n\n");
                pgdata.setAlignment(Element.ALIGN_RIGHT);
                document.add(pgdata);
                listaVendas = daoVenda.getVendasDiarias(dataVendaParam);
                if (!listaVendas.isEmpty()) {

                    document.add(this.inserirTabelaVendasDiaria(listaVendas));
                } else {
                    Paragraph pgNenhumaVenda = new Paragraph("Nenhuma venda realizada.");
                    pgNenhumaVenda.setAlignment(Element.ALIGN_CENTER);
                    document.add(pgNenhumaVenda);
                }
                double valorTotalVenda = this.calculaValorTotalVenda(listaVendas);

                String strValorTotalVenda = String.format("%.2f", valorTotalVenda).replace(".", ",");
                Paragraph pgValorTotal = new Paragraph("Valor Total: R$ " + strValorTotalVenda);
                pgValorTotal.setAlignment(Element.ALIGN_RIGHT);
                document.add(pgValorTotal);
            } catch (DocumentException | FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            } finally {
                document.close();
            }
            Desktop.getDesktop().open(new File(fileDirectory + nomeArquivo));
        }
    }

    /**
     * Esta função retorna a tabela de vendas a ser inserida no pdf de vendas diárias.
     */
    
    private PdfPTable inserirTabelaVendasDiaria(ArrayList<Venda> listaVendas) throws DocumentException {

        PdfPTable table = new PdfPTable(new float[]{20f, 20f});

        PdfPCell celulaIdVenda = new PdfPCell(new Phrase("Código da Venda"));
        celulaIdVenda.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell celulaValorVenda = new PdfPCell(new Phrase("Valor (R$)"));
        celulaValorVenda.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(celulaIdVenda);
        table.addCell(celulaValorVenda);

        for (Venda venda : listaVendas) {
            PdfPCell celula1 = new PdfPCell(new Phrase(Integer.toString(venda.getIdVenda())));
            PdfPCell celula2 = new PdfPCell(new Phrase(Double.toString(venda.getValorVenda()).replace(".", ",")));

            celula1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celula2.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(celula1);
            table.addCell(celula2);
        }
        return table;
    }

    /**
     * Esta função salva o Pdf de Vendas Mensais no diretório desejado.
     */
    
    public void salvarPdfVendaMensal(int mes, int ano) throws IOException {

        VendaDAO daoVenda = new VendaDAO();
        ArrayList<Venda> listaVendas = new ArrayList<>();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        Document document = new Document();
        String fileDirectory = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecionar a Pasta...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(new JPanel());
        String nomeArquivo = "/Vendas" + mes + ano + ".pdf";

        if (result == JFileChooser.APPROVE_OPTION) {
            fileDirectory = fileChooser.getSelectedFile().getPath();

            try {

                PdfWriter.getInstance(document, new FileOutputStream(fileDirectory + nomeArquivo));
                document.open();
                document.add(new Paragraph("Vendas"));
                Paragraph pgdata = new Paragraph(this.toStringMes(mes) + "/" + ano + "\n\n");
                pgdata.setAlignment(Element.ALIGN_RIGHT);
                document.add(pgdata);
                listaVendas = daoVenda.getVendasMensais(mes, ano);
                if (!listaVendas.isEmpty()) {

                    document.add(this.inserirTabelaVendasMensal(listaVendas));
                } else {
                    Paragraph pgNenhumaVenda = new Paragraph("Nenhuma venda realizada.");
                    pgNenhumaVenda.setAlignment(Element.ALIGN_CENTER);
                    document.add(pgNenhumaVenda);
                }
                double valorTotalVenda = this.calculaValorTotalVenda(listaVendas);

                String strValorTotalVenda = String.format("%.2f", valorTotalVenda).replace(".", ",");
                Paragraph pgValorTotal = new Paragraph("Valor Total: R$ " + strValorTotalVenda);
                pgValorTotal.setAlignment(Element.ALIGN_RIGHT);
                document.add(pgValorTotal);
            } catch (DocumentException | FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            } finally {
                document.close();
            }
            Desktop.getDesktop().open(new File(fileDirectory + nomeArquivo));
        }
    }

    /**
     * Esta função retorna a tabela de vendas a ser inserida no pdf de vendas mensais.
     */  
    private PdfPTable inserirTabelaVendasMensal(ArrayList<Venda> listaVendas) throws DocumentException {

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        PdfPTable table = new PdfPTable(new float[]{20f, 20f, 20f});

        PdfPCell celulaIdVenda = new PdfPCell(new Phrase("Código da Venda"));
        celulaIdVenda.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell celulaValorVenda = new PdfPCell(new Phrase("Valor (R$)"));
        celulaValorVenda.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell celulaDataVenda = new PdfPCell(new Phrase("Data"));
        celulaValorVenda.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(celulaIdVenda);
        table.addCell(celulaValorVenda);
        table.addCell(celulaDataVenda);

        for (Venda venda : listaVendas) {
            PdfPCell celula1 = new PdfPCell(new Phrase(Integer.toString(venda.getIdVenda())));
            PdfPCell celula2 = new PdfPCell(new Phrase(Double.toString(venda.getValorVenda()).replace(".", ",")));
            PdfPCell celula3 = new PdfPCell(new Phrase(f.format(venda.getDataVenda())));

            celula1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celula2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celula3.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(celula1);
            table.addCell(celula2);
            table.addCell(celula3);
        }
        return table;
    }

    /**
     * Esta função retorna o valor total das vendas.
     */
    
    private double calculaValorTotalVenda(ArrayList<Venda> listaVenda) {

        double valorTotalVenda = 0;

        for (Venda venda : listaVenda) {
            valorTotalVenda += venda.getValorVenda();
        }

        return valorTotalVenda;
    }

    /**
     * Esta função retorna o mês.
     */
    private String toStringMes(int i) {

        String[] nomesMeses = {"Janeiro", "Fevereiro",
            "Março", "Abril", "Maio", "Junho", "Julho",
            "Agosto", "Setembro", "Outubro",
            "Novembro", "Dezembro"
        };

        return nomesMeses[i-1];
    }

}
