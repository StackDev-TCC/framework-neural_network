package report;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.RenderingHints;

/**
 * Classe responsável por plotar os gráficos gerados através dos dados coletados por
 * {@link learning.Training} e {@link kernel.NeuralNetwork} agrupados em {@link Report}
 *
 * <p>O uso básido desta classe está configurado da seguinte maneira: O {@link learning.Training} realiza
 * seus métodos de treinamento. A cada nova iteração, ele acumula em uma {@code String} o estado atual
 * das {@link kernel.Layer Layers}, {@link kernel.Neuron Neurons} e {@link kernel.Connection Connections} e
 * a variação de erros e acertos em um {@code Array} inteiro</p>
 *
 * <p>Na sequência, a classe {@link Report} concentra estes dados para tanto formatar a saida da
 * {@code String} para {@code html} como chamar o construtor de {@code ImageU} passando como parâmetro
 * um {@link PixelCalc} e o {@code Array} acumulado em {@link Report} </p>
 */
public class ImageU {

    /**
     * Ponto x0 da imagem
     */
    private double x0;
    /**
     * Ponto y0 da imagem
     */
    private double y0;
    /**
     * Ponto x1 da imagem
     */
    private double x1;
    /**
     * Ponto y1 da imagem
     */
    private double y1;

    /**
     * Valor de referência para o mapeamento no eixo x
     */
    private double x;
    /**
     * Valor de origem inicial X
     */
    private double oix;
    /**
     * Valor da origem final X
     */
    private double ofx;
    /**
     * Valor do destino inicial X
     */
    private double dix;
    /**
     * Valor do destino final X
     */
    private double dfx;
    /**
     * Ponto em x a ser mapeado
     */
    private double pxr;

    /**
     * Valor de referência para o mapeamento no eixo Y
     */
    private double y;
    /**
     *  Valor da origem inicial em Y
     */
    private double oiy;
    /**
     * Valor da origem final em Y
     */
    private double ofy;
    /**
     * Valor do destino inicial em Y
     */
    private double diy;
    /**
     * Valor do destino final em Y
     */
    private double dfy;
    /**
     * Valor a ser mapeado em Y
     */
    private double pyr;

    /**
     * Ineiro para eixo X
     */
    int eixoX;
    /**
     * Inteiro para eixo Y
     */
    int eixoY;

    /**
     * Rótulo em {@code String} para o eixo X
     */
    String xLabel;
    /**
     * Coordenada horizontal para {@code xLabel}
     */
    double w = 1;

    /**
     * Rótulo em {@code String} para o eixo Y
     */
    String yLabel;
    /**
     * Coordenada vertical para o {@code yLabel}
     */
    int z = 1;

    /**
     * Valor d
     */
    double porcentagem;

    BufferedImage report;
    Graphics2D render;
    PixelCalc pixelcalc;

    List<Double> auxList;
    List<Point> pontos;

    int [] data;

    public ImageU(PixelCalc pixelcalc, int[] data) {
        this.pixelcalc = pixelcalc;
        this.data = data;
    }

    public double getMaxValue() {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < auxList.size(); i++) {
            if (auxList.get(i) > max) {
                max = auxList.get(i);
            }
        }
        return max;
    }

    public double getMinValue() {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < auxList.size(); i++) {
            if (auxList.get(i) < min) {
                min = auxList.get(i);
            }
        }
        return min;
    }

    public void background() {
        if (pixelcalc.getPixels_width() == null && pixelcalc.getPixels_height() == null) {
            pixelcalc = new PixelCalc();
            pixelcalc.defaultImage();
        }
        report = new BufferedImage(pixelcalc.getPixels_width(), pixelcalc.getPixels_height(), BufferedImage.TYPE_INT_RGB);
        render = (Graphics2D) report.getGraphics();
        render.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        render.setColor(Color.lightGray);
        render.fillRect(0, 0, report.getWidth(), report.getHeight());
    }

    public double margem() {
        return report.getWidth() * this.porcentagem;
    }

    public void whiteBrackground() {


        this.porcentagem = 0.05;
        x0 = margem();
        x1 = report.getWidth() - margem() - 1;
        y0 = margem();
        y1 = report.getHeight() - 1;


        render.setColor(Color.WHITE);
        render.fillRect((int) x0, (int) y0 - 15, (int) x1 - (int) x0 + 15, (int) y1 - (int) y0);
        setTitulo();

    }

    public void setTitulo() {
        render.setColor(Color.BLACK);
        render.drawString("Gráfico Projeto: Rede Neural", (int) x0, (int) y0 - 20);
    }

    public List<Double> valoresSaida() {
        auxList = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            auxList.add((double)data[i]);
        }
        return auxList;
    }

    public void mapeamentoX() {
        pxr = ((x - oix) * (dfx - dix) / (ofx - oix)) + dix;
    }

    public void mapeamentoY() {
        pyr = ((y - ofy) * (diy - dfy) / (oiy - ofy)) + dfy;
    }

    public void eixoXY() {
        porcentagem = 0.1;

        render.setColor(Color.BLACK);
        render.drawLine((int) x0 + 75, (int) y1 - (int) margem(), (int) x1, (int) y1 - (int) margem()); // eixo x
        render.drawLine((int) x0 + (int) margem(), (int) y0, (int) x0 + (int) margem(), (int) y1 - 75); // eixo Y

    }

    public void criaLinhasPontos() {
        pontos = new ArrayList<>();
//        valoresSaida();
        System.out.println(valoresSaida());

        porcentagem = 0.1;

        oix = 0;
        ofx = auxList.size();
        dix = (int) (x0 + margem() + 1);
        dfx = x1;

        oiy = getMaxValue();
        ofy = getMinValue();
        diy = y0;
        dfy = (int) (y1 - margem());

        for (int i = 0; i < auxList.size(); i++) {
            x = i;
            mapeamentoX();

            // System.out.println(Prx);
            for (int j = 0; j < auxList.size(); j++) {
                y = auxList.get(i);
                mapeamentoY();
                pontos.add(new Point((int) pxr, (int) pyr));
            }
        }

        for (int a = 0; a < pontos.size() - 1; a++) {
            render.setColor(Color.blue);
            render.drawLine(pontos.get(a).x, pontos.get(a).y, pontos.get(a + 1).x, pontos.get(a + 1).y);
        }
    }

    public void criaPontos() {

        porcentagem = 0.1;

        oix = 0;
        ofx = auxList.size();
        dix = (int) (x0 + margem());
        dfx = x1;

        oiy = getMaxValue();
        ofy = getMinValue();
        diy = y0;
        dfy = (int) (y1 - margem());

        for (int i = 0; i < auxList.size(); i++) {
            x = i;
            mapeamentoX();


            for (int j = 0; j < auxList.size(); j++) {
                y = auxList.get(i);
                mapeamentoY();

                render.setColor(Color.RED);
                render.fillOval((int) pxr - 4, (int) pyr - 4, 8, 8);
            }
        }
    }

    public void criaGrade() {

        Stroke oldStroke = render.getStroke();
        float[] dash = {2f, 0f, 2f};
        BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        render.setStroke(bs);

        porcentagem = 0.1;

        oix = 0;
        ofx = auxList.size() / 2;
        dix = (int) (x0 + margem());
        dfx = x1;

        oiy = getMaxValue();
        ofy = getMinValue();
        diy = y0;
        dfy = (int) (y1 - margem());


        double auxFixa = (double) auxList.size() / ofx;
        w = auxFixa;

        //X
        x = 0;
        for (int i = 0; i < auxList.size(); i++) {
            mapeamentoX();
            for (int a = 0; a < auxList.size(); a++) {
                render.setColor(Color.lightGray);
                render.drawLine((int) pxr, (int) (y1 - margem()), (int) pxr, (int) y0);

                if ((double) auxList.size() % ofx == 0) {
                    xLabel = Math.round(w) + "";
                } else {
                    xLabel = Math.round(w * 100.0) / 100.0 + "";
                }
                FontMetrics metrics = render.getFontMetrics();
                int labelWidthx = metrics.stringWidth(xLabel);

                render.setColor(Color.BLACK);
                render.drawString(xLabel, (int) pxr - labelWidthx / 2, (int) y1 - (int) margem() + metrics.getHeight() + 5);
            }
            w = w + auxFixa;
            x++;
        }

        //Y
        y = getMinValue();
        //y = 1;
        for (int j = 0; j < 11; j++) {

            mapeamentoY();
            for (int i = 0; i < 11; i++) {
                render.setColor(Color.lightGray);
                render.drawLine((int) (x0 + margem()), (int) pyr, (int) x1, (int) pyr);


                yLabel = Math.round(y * 100.00) / 100.00 + "";


                FontMetrics metrics = render.getFontMetrics();
                int labelWidthy = metrics.stringWidth(yLabel);

                render.setColor(Color.BLACK);
                render.drawString(yLabel, (int) x0 + (int) margem() - labelWidthy - 10, (int) pyr + metrics.getHeight() / 2);
            }
            z++;
            y += ((getMaxValue() - getMinValue()) / 10);
            //y++;

        }
        render.setStroke(oldStroke);
    }

    public void saveImage(String nameGraph) {
        try {
            ImageIO.write(report, "PNG", new File(nameGraph + ".png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void criaImagem(String nameGraph) {
        valoresSaida();
        background();
        whiteBrackground();
        criaGrade();
        eixoXY();
        criaLinhasPontos();
        criaPontos();
        saveImage(nameGraph);
    }

}