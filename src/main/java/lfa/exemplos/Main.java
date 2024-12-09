package lfa.exemplos;

import javax.smartcardio.ATR;

public class Main {

    public static final String TIPOS_JAVA = "(int|double|boolean|char|String|float|long|short|byte|void)";

    // Definições padrão.
    public static final String BRANCO = "(\\s)"; // [ \t\n\r\f\v] onde
    public static final String BRANCOS = BRANCO + "*";
    public static final String DIGITO = "([0-9])";
    public static final String DIGITOS = DIGITO + "*";
    public static final String LETRA = "([A-Za-z])";
    public static final String LETRAS = LETRA + "*";
    public static final String IDENT = "(" + LETRA + "(" + LETRA + "|" + DIGITO + ")*)";
    public static final String EXPONENCIAL = "(E(\\+|-)" + DIGITOS + ")";
    public static final String REAL = "((\\-)?" + DIGITOS + "(\\." + DIGITOS + ")?( " + EXPONENCIAL + ")?)";
    public static final String INTEIRO = "(" + DIGITOS + EXPONENCIAL + "?)";
    public static final String NUMEROS = "(" + INTEIRO + "|" + REAL + ")";
    public static final String ATRIBUICAO = IDENT + BRANCOS + "=" + BRANCOS + "(" + REAL + "|" + "\"[^\"]*\"" + ")";


    // Definições que eu criei

    public static final String TERM = "(" + BRANCOS  + ".*" +  BRANCOS + ")";

    public static final String ABRE_PARENTESIS = "\\("; // Matches '('
    public static final String FECHA_PARENTESIS = "\\)"; // Matches ')'

    public static final String DEFINICAO = TIPOS_JAVA + BRANCOS + IDENT;
    public static final String DEFINICOES = "(" + DEFINICAO + "((" + BRANCOS + "," + BRANCOS + DEFINICAO + BRANCOS + ")?)*" + ")?" + "(" + BRANCOS + ")";

    public static final String DEFINICAO_ATRIB = "(" + TIPOS_JAVA + BRANCOS + ")" + ATRIBUICAO;
    public static final String DEFINICOES_ATRIB = DEFINICAO_ATRIB + "((" + BRANCOS + "," + BRANCOS + DEFINICAO_ATRIB + ")?)*";



    public static final String PARAMS = ABRE_PARENTESIS + "(" + IDENT + BRANCOS + "((" + BRANCOS + "\\," + BRANCOS + "(" + IDENT + "|" + REAL + ")" + ")?)*" + ")?" + BRANCOS + FECHA_PARENTESIS;

    public static final String OPR = "(\\+|\\-|\\*|\\/|%)";

    public static final String COMPARADOR = "(>|<|==|!=|<=|=<|>=|=>)";

    public static final String ATRIB = "\\." + IDENT;

    public static final String METODO = ATRIB + PARAMS;

    public static final String VAR_FUNC_ARR_IDENT = IDENT + BRANCOS + "(" + PARAMS + ")?" + "((" + "((\\[" + BRANCOS + "(" + IDENT + "|" + REAL + ")" + BRANCOS + "\\])?)*" + "((" + ATRIB + ")?)*" + "((" + METODO + ")?)*" + ")?)*";

    public static final String VALOR = "(" + VAR_FUNC_ARR_IDENT + "|" + REAL + ")";

    public static final String EXPR = VALOR + "((" + BRANCOS + OPR + BRANCOS + VALOR + ")?)*";

    /**
     * *****************************************
     * Trabalho 02
     *
     * Faças as Expressoes Regulares para reconhecer: 1. Assinatura de funções
     * ex: void funcao1(int a, float b) ex: String funcao2()
     *
     * 2. Parametros de funções ex: int a, float b ex: float media, String nome
     *
     * 3. Condicional ex: if(ano < 1990) ex: if(3*a != 4+t)
     *
     * 4. Expressão matemática ex: 3+media/3 ex: -4+beta*media[1].x ex: soma(a,
     * b)/4*vetor[5].idade
     *
     * OBS_1: Muitos erros ocorrem por falta de parenteses em torno da
     * expressão, logo use e abuse dos parenteses, eles nunca serão demais.
     *
     * OBS_2: A cada nova Expressao Regualar feita, teste-a.
     * ******************************************
     */
    public static void main(String[] args) {
        String exp;

        exp = TIPOS_JAVA + BRANCOS + IDENT + BRANCOS + ABRE_PARENTESIS + DEFINICOES + FECHA_PARENTESIS;

        ExpressaoRegular assinaturaFuncao = new ExpressaoRegular(exp);
        assinaturaFuncao.confere("void funcao1(int a, float b)");
        assinaturaFuncao.confere("String funcao2()");

        exp = DEFINICOES;
        ExpressaoRegular parametrosFuncao = new ExpressaoRegular(exp);
        parametrosFuncao.confere("int a, float b ");
        parametrosFuncao.confere("float media, String nome");

        exp = "if" + BRANCOS + ABRE_PARENTESIS + BRANCOS + EXPR + BRANCOS  + "((" + COMPARADOR + BRANCOS + EXPR + BRANCOS + ")?)*" + FECHA_PARENTESIS;
        ExpressaoRegular condicional = new ExpressaoRegular(exp);
        condicional.confere("if(ano < 1990)");
        condicional.confere("if(data.ano.toInt() < periodo[x])");
        condicional.confere("if(3*a != 4+t)");

        exp = EXPR;
        ExpressaoRegular expressoesAritmeticas = new ExpressaoRegular(exp);
        expressoesAritmeticas.confere("3+media/3");
        expressoesAritmeticas.confere("-4+beta*media[1].x");
        expressoesAritmeticas.confere("soma(a,b)/4*vetor[5].idade");
        expressoesAritmeticas.confere("232+2/3232.232-var[32]/prova[pro][xesq].pro()[232].qes().regex.gors(a, 2330, 23.4)");

        // Número positivo e ímpar, em binário
        exp = "0" + "(0|1)*" + "1";
        ExpressaoRegular binPositivoImpar = new ExpressaoRegular(exp);
        binPositivoImpar.confere("0001010101010101");
        binPositivoImpar.confere("011001101101");
        binPositivoImpar.confere("01");

        // Número negativo e par, em binário
        exp = "1" + "(0|1)*" + "0";
        ExpressaoRegular binNegativoPar = new ExpressaoRegular(exp);
        binNegativoPar.confere("10110110110");
        binNegativoPar.confere("101001110");
        binNegativoPar.confere("111010100110");

        // Números maiores ou igual a oito, em binário
        exp = "0" + "((0)?)*" + "(1)" + "(0|1){3,}$";
        ExpressaoRegular difTres = new ExpressaoRegular(exp);
        difTres.confere("0001010101010101");
        difTres.confere("011110");
        difTres.confere("01000");



    }
}
