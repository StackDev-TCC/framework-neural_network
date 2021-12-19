package report;

import Utils.PixelCalc;
import kernel.NeuralNetwork;
import operations.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A classe {@code Report} é responsável por transformar os dados de uma {@link kernel.NeuralNetwork},
 * seu treinamento por {@link TrainingNetwork} e seu uso, em um documento {@code html}.
 *
 * <p>Para tanto, ela deve se tornar membro de  {@link TrainingNetwork} ou {@link UsingNetwork}, e receber
 * seus parâmetros conforme forem calculados.</p>
 *
 * <p>
 *     O usuário tem acesso à toda configuração dos elementos do {@code Report}. Ele pode definir o tamanho
 *     das imagens, o limite do buffer de exibição dos elementos a serem plotados e o tamanho da fonte.
 * </p>
 */
public class Report {
    /**
     * Gráfico que plota as diferenças de totais de erros geradas pelo {@link TrainingNetwork} da
     * {@link kernel.NeuralNetwork}
     */
    private PlotGraphics errorTracking;
    /**
     * Gráfico que plota a variação de tempo entre treinamentos até o momento onde o erro atingir um
     * nível tolerável
     */
    private PlotGraphics timeComparison;
    /**
     * {@code Array} de duas dimensões contendo a resolução das imagens que aparecerão no {@code Report}
     */
    private int[] graphResolution;
    /**
     * Inteiro que armazena o tamanho da fonte a ser configurada no {@code CSS} do {@code html}
     */
    private int fontSize;

    private String htmlpage;



    /**
     * Construtor da classe {@code Report}, onde é possível definir a largura e altura das gráficos
     * a serem plotados e o tamanho da fonte que será configurada no {@code CSS} do {@code html}.
     * @param w A largura do gráfico.
     * @param h A altura do gráfico.
     * @param margin A {@code margin} a ser passada para o {@link PlotGraphics}.
     * @param fontSize o tamanho da fonte do texto em {@code html}
     */
    public Report(int w, int h, float margin, int fontSize) {
        this.graphResolution = new int[]{w,h};
        this.errorTracking = new PlotGraphics(w,h, margin,"repetições", "erros", "Error Behavior");
        this.timeComparison = new PlotGraphics(w,h);
        this.fontSize = fontSize;
    }

    /**
     * Método da classe {@code Report} que retorna o gráfico de acompanhamento de erros durante
     * o treinamento de uma determinada {@link kernel.NeuralNetwork}
     * @return o {@link PlotGraphics} deacompanhamneto de erros
     */
    public PlotGraphics getErrorTracking() {
        return errorTracking;
    }

    /**
     * Método da classe {@code Report} que modifica o gráfico de acompanhamento de erros durante o
     * treinamento de uma determinada {@link kernel.NeuralNetwork}.
     * @param errorTracking o novo {@link PlotGraphics} para substituir o acompanhamento de erros anterior
     */
    public void setErrorTracking(PlotGraphics errorTracking) {
        this.errorTracking = errorTracking;
    }

    /**
     * Método da classe {@code Report} que retorna a comparação de tempo {@code timeComparison}.
     * @return comparação de tempo
     */
    public PlotGraphics getTimeComparison() {
        return timeComparison;
    }

    /**
     * Método da classe {@code Report} que define a comparação de tempo {@code timeComparison}.
     * @param timeComparison a comparação de tempo
     */
    public void setTimeComparison(PlotGraphics timeComparison) {
        this.timeComparison = timeComparison;
    }

    /**
     * Método da classe {@code Report} que retorna a resolução do gráfico {@code timeComparison}.
     * @return resolução do gráfico
     */
    public int[] getGraphResolution() {
        return graphResolution;
    }

    /**
     * Método da classe {@code Report} que define o {@code Report#int[]} a resolução do gráfico {@code graphResolution}.
     * @param graphResolution resolução do gráfico
     */
    public void setGraphResolution(int[] graphResolution) {
        this.graphResolution = graphResolution;
    }

    /**
     * Método da classe {@code Report} que define o {@code Report#int[]} a resolução do gráfico {@code graphResolution}.
     * @param w lagura
     * @param h altura
     */
    public void setGraphResolution(int w, int h){this.graphResolution = new int[]{w,h};}

    /**
     * Método da classe {@code Report} que retorna o tamanho da fonte usada no {@code html}.
     * @return o tamanho da fonte.
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Método da classe {@code Report} que altera o tamanho da fonte a ser utilizada no {@code html}
     * @param fontSize o tamanho da fonte a ser usada no relatório {@code html}
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Método principal da classe {@code Report}, que vai transformar os dados em página {@code html} com
     * a plotagem dos gráficos
     */
    public void generateReport(){
        errorTracking.plot();
        timeComparison.plot();

    }
//
//    public static String generate(String result, int[]error_reduction) {
//
//        StringBuilder sb = new StringBuilder();
//
//        PlotGraphics img = new PlotGraphics(new PixelCalc(),error_reduction);
//        img.criaImagem("Graph");
//
//        String report_name = "Report teste";
//        String html_head = "<!DOCTYPE html>\n" +
//                "<html lang='pt-br'>\n" +
//                "<head>\n" +
//                "  <meta charset='UTF-8'>\n" +
//                "  <meta http-equiv='X-UA-Compatible' content='IE=edge'>\n" +
//                "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
//                "  <title> " + report_name + "</title>\n" +
//                "</head>\n";
//
//        String html_body_open_tag = "<body>";
//
//        String html_container_open_tag = "<div class='container'>";
//        String html_content_container_open_tag = "<div class='content-container'>";
//        String div_close_tag = "</div>";
//
//        String[] lines = result.split("\n");
//
//        sb.append(report_name);
//        sb.append(html_head);
//        sb.append(html_body_open_tag);
//        sb.append(html_container_open_tag);
//        sb.append(html_content_container_open_tag);
//
//        for(String l: lines) {
//            sb.append("<span>" + l + "</span>");
//        }
//
//        sb.append(div_close_tag);
//
//        String html_img = "<img src='Graph.png'>";
//
//        sb.append(div_close_tag);
//
//        String css = "   <style>\n" +
//                "        * {\n" +
//                "            margin: 0;\n" +
//                "            padding: 0;\n" +
//                "            box-sizing: border-box;\n" +
//                "            outline: none !important;\n" +
//                "        }\n" +
//                "\n" +
//                "        body {\n" +
//                "            display: flex;\n" +
//                "            flex-direction: column;\n" +
//                "        }\n" +
//                "\n" +
//                "        html,\n" +
//                "        body {\n" +
//                "            min-height: 100vh;\n" +
//                "        }\n" +
//                "\n" +
//                "        .container {\n" +
//                "            display: flex;\n" +
//                "            width: 100%;\n" +
//                "            max-width: 1160px;\n" +
//                "            margin: 0 auto;\n" +
//                "        }\n" +
//                "\n" +
//                "        .content-container {\n" +
//                "            display: flex;\n" +
//                "            width: 100%;\n" +
//                "            flex-direction: column;\n" +
//                "        }\n" +
//                "    </style>";
//
//        String html_body_close_tag = "</body>";
//
//        String html_close_tag = "</html>";
//
//        sb.append(css);
//        sb.append(html_body_close_tag);
//        sb.append(html_close_tag);
//
//        return sb.toString();
//    }

//    public static void reportPrint(String filePath, String content) {
//
//        FileWriter fw = null;
//        try {
//            fw = new FileWriter(new File(filePath + ".html"));
//            fw.write(content);
//            fw.flush();
//            fw.close();
//        } catch (Exception e) {
//            System.out.println("Erro bagulho doido");
//        } finally {
//            if (fw != null) {
//                try {
//                    fw.close();
//                    System.out.println("Arquivo salvo");
//                } catch (IOException e) {
//                    System.out.println("Erro ao fechar o aquivo");
//                }
//            }
//        }
//    }

}


