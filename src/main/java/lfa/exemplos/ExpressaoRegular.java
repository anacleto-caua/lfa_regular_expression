/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lfa.exemplos;

/**
 *
 * @author jose
 */
public class ExpressaoRegular {

    public String expressao;

    public ExpressaoRegular(String expressao) {
        this.expressao = expressao;
    }

    public void confere(String sentenca) {
        if ((sentenca != null) && !sentenca.isEmpty()) {
            if (sentenca.matches(expressao)) {
                System.out.println("W:'" + sentenca + "'........ ACEITA!");
            } else {
                System.err.println("W:'" + sentenca + "'........ rejeitada.");
            }
        } else {
            System.err.println("Sentença vazia.");
        }
    }

}
