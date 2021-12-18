package report;


import Utils.PixelCalc;
import operations.TrainingNetwork;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Classe responsável por plotar os gráficos gerados através dos dados coletados por
 * {@link TrainingNetwork} e {@link kernel.NeuralNetwork} agrupados em {@link Report}
 *
 * <p>O uso básido desta classe está configurado da seguinte maneira: O {@link TrainingNetwork} realiza
 * seus métodos de treinamento. A cada nova iteração, ele acumula em uma {@code String} o estado atual
 * das {@link kernel.Layer Layers}, {@link kernel.Neuron Neurons} e {@link kernel.Connection Connections} e
 * a variação de erros e acertos em um {@code Array} inteiro</p>
 *
 * <p>Na sequência, a classe {@link Report} concentra estes dados para tanto formatar a saida da
 * {@code String} para {@code html} como chamar o construtor de {@code ImageU} passando como parâmetro
 * um {@link PixelCalc} e o {@code Array} acumulado em {@link Report} </p>
 */
public class PlotGraphics {

    private Universe u = null;
    private double[] data = null;
    private int w, h;
    private BufferedImage plot = null;
    private Graphics2D render = null;
    private String xLabel;
    private String yLabel;
    private String title;
    private float percent;
//
//    /**
//     * Ponto x0 da imagem
//     */
//    private double x0;
//    /**
//     * Ponto y0 da imagem
//     */
//    private double y0;
//    /**
//     * Ponto x1 da imagem
//     */
//    private double x1;
//    /**
//     * Ponto y1 da imagem
//     */
//    private double y1;
//
//    /**
//     * Valor de referência para o mapeamento no eixo x
//     */
//    private double x;
//    /**
//     * Valor de origem inicial X
//     */
//    private double oix;
//    /**
//     * Valor da origem final X
//     */
//    private double ofx;
//    /**
//     * Valor do destino inicial X
//     */
//    private double dix;
//    /**
//     * Valor do destino final X
//     */
//    private double dfx;
//    /**
//     * Ponto em x a ser mapeado
//     */
//    private double pxr;
//
//    /**
//     * Valor de referência para o mapeamento no eixo Y
//     */
//    private double y;
//    /**
//     *  Valor da origem inicial em Y
//     */
//    private double oiy;
//    /**
//     * Valor da origem final em Y
//     */
//    private double ofy;
//    /**
//     * Valor do destino inicial em Y
//     */
//    private double diy;
//    /**
//     * Valor do destino final em Y
//     */
//    private double dfy;
//    /**
//     * Valor a ser mapeado em Y
//     */
//    private double pyr;
//
//    /**
//     * Ineiro para eixo X
//     */
//    int eixoX;
//    /**
//     * Inteiro para eixo Y
//     */
//    int eixoY;
//
//
//    double w = 1;
//
//    /**
//     * Rótulo em {@code String} para o eixo Y
//     */
//    String yLabel;
//    /**
//     * Coordenada vertical para o {@code yLabel}
//     */
//    int z = 1;
//
//    /**
//     * Valor d
//     */
//    double porcentagem;
//
//
//    PixelCalc pixelcalc;
//
//    List<Double> auxList;
//    List<Point> pontos;
//
//

    /**
     * Construtor de {@code PlotGraphics} com todos os parâmetros necessários para a plotagem de um gráfico.
     * @param w A largura da Imagem geradora do gráfico.
     * @param h A altura da Imagem geradora do gráfico.
     * @param xLabel O rótulo do eixo X.
     * @param yLabel O rótulo do eixo Y.
     * @param title O titulo do gráfico.
     * @param data O {@code Array} de elementos a serem plotados.
     */
    public PlotGraphics(int w, int h,String xLabel, String yLabel, String title, double[] data){
        this.w = w;
        this.h = h;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.title = title;
        this.data = data;

        this.u = generateU();

    }

    /**
     * Construtor de {@code PlotGraphics} com os parâmetros iniciais para a criação do objeto
     * antes de se ter calculado os dados para serem plotados
     * @param w A largura da Imagem geradora do gráfico.
     * @param h A altura da Imagem geradora do gráfico.
     * @param xLabel O rótulo do eixo X.
     * @param yLabel O rótulo do eixo Y.
     * @param title O titulo do gráfico.
     */
    public PlotGraphics(int w, int h, String xLabel, String yLabel, String title){
        this.w = w;
        this.h = h;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.title = title;
    }


    /**
     * Construtor de {@code PlotGraphics} com largura e altura. Calcula o {@link Universe} quando receber
     * o parâmetro {@code data} sem {@code Labels} nem {@code Title}.
     * @param w A largura da Imagem geradora do gráfico.
     * @param h A altura da Imagem geradora do gráfico.
     */
    public PlotGraphics(int w, int h){
        this.w = w;
        this.h = h;
    }

    private double getMaxValue() {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    private double getMinValue() {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }

    /**
     * Método de {@code PlotGraphics} que insere um novo {@code Array} para ser dimensionado e plotado.
     * @param data o {@code Array} para ser plotado.
     */
    public void setData(double[] data){
        this.data = data;
        this.u = generateU();
    }

    private Universe generateU(){
        if(data != null) {
            double xmin = 0;
            double xmax = data.length;
            double ymin = getMinValue();
            double ymax = getMaxValue();
            return new Universe(xmin, xmax, ymin, ymax);
        }
        return null;
    }

//    private void background() {
//        if (pixelcalc.getPixels_width() == null && pixelcalc.getPixels_height() == null) {
//            pixelcalc = new PixelCalc();
//            pixelcalc.defaultImage();
//        }
//        report = new BufferedImage(pixelcalc.getPixels_width(), pixelcalc.getPixels_height(), BufferedImage.TYPE_INT_RGB);
//        render = (Graphics2D) report.getGraphics();
//        render.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        render.setColor(Color.lightGray);
//        render.fillRect(0, 0, report.getWidth(), report.getHeight());
//    }

    private double margem() {
        return plot.getWidth() * this.percent;
    }

//    private void whiteBrackground() {
//
//        this.porcentagem = 0.05;
//        x0 = margem();
//        x1 = report.getWidth() - margem() - 1;
//        y0 = margem();
//        y1 = report.getHeight() - 1;
//
//        render.setColor(Color.WHITE);
//        render.fillRect((int) x0, (int) y0 - 15, (int) x1 - (int) x0 + 15, (int) y1 - (int) y0);
//        setTitulo("Gráfico da bagaça");
//
//    }

    /**
     * Método de {@code PlotGraphics} que permite a definição do titulo da plotagem da imagem de forma parametrizada
     * @param title titulo do gráfico
     */
    public void setTitulo(String title) {
        render.setColor(Color.BLACK);
        render.drawString(title, w/2 - (render.getFontMetrics().stringWidth(title)/2) ,  20);
    }

    /**
     * Método que retorna o {@link Universe} presente no objeto {@code PlotGraphics} ou {@code null}.
     * @return O universo instanciado.
     */
    public Universe getU() {
        return u;
    }

    /**
     * Método de {@code PlotGraphics} que cria um novo universo para {@code PlotGraphics} caso não contenha um, ou assume um novo {@link Universe} caso já possua.
     * @param u Universo a ser assumido.
     */
    public void setU(Universe u) {
        this.u = u;
    }

    /**
     * Método de {@code PlotGraphics} que retorna o {@code Array} de dados presente no objeto {@code plotGraphics}.
     * @return {@code Array} de dados
     */
    public double[] getData() {
        return data;
    }

    /**
     *Método de {@code PlotGraphics} que retrona a largura da imagem de plotagem
     * @return A largura {@code w} da imagem de plotagem
     */
    public int getW() {
        return w;
    }

    /**
     * Método de {@code PlotGraphics} que insere uma nova largura {@code w} para a imagem de plotagem
     * @param w a largura {@code w} da imagem de plotagem
     */
    public void setW(int w) {
        //TODO ao atualizar uma das dimensões w ou h da imagem, é necessário remodelar a BufferedImage
        this.w = w;
    }

    /**
     * Método de {@code PlotGraphics} que retorna a altura da imagem de plotagem.
     * @return A altura {@code h} da imagem de plotagem
     */
    public int getH() {
        return h;
    }

    /**
     * Método de {@code PlotGraphics} que insere uma nova altura {@code h} para a imagem de plotagem.
     * @param h a altura {@code h} da imagem de plotagem.
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * Método de {@code PlotGraphics} que retorna graphic da image {@code render}.
     * @return Graphic image render
     */
    public Graphics2D getRender() {
        return render;
    }

    /**
     * Método de {@code PlotGraphics} que insere um novo graphic da image {@code render}.
     * @param render a graphic image render {@code render}.
     */
    public void setRender(Graphics2D render) {
        this.render = render;
    }

    /**
     * Método de {@code PlotGraphics} que retorna o rótulo do eixo X{@code xLabel}.
     * @return rótulo do eixo x.
     */
    public String getxLabel() {
        return xLabel;
    }

    /**
     * Método de {@code PlotGraphics} que define o valor do label do eixo X através do parâmetro {@code xLabel}.
     * @param xLabel o título do label do eixo X.
     */
    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    /**
     * Método de {@code PlotGraphics} que retorna o rótulo do eixo Y{@code yLabel}.
     * @return rótulo do eixo x.
     */
    public String getyLabel() {
        return yLabel;
    }

    /**
     * Método de {@code PlotGraphics} que define o valor do label do eixo Y através do parâmetro {@code yLabel}.
     * @param yLabel o rótulo do eixo Y.
     */
    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    /**
     * Método de {@code PlotGraphics} que retornar o título {@code title}
     * @return título
     */
    public String getTitle() {
        return title;
    }

    /**
     * Método de {@code PlotGraphics} que retornar o título {@code title}
     * @param title título
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Método de {@code PlotGraphics} que retornar o plot {@code plot}
     * @return renderização
     */
    public BufferedImage getPlot() {
        return plot;
    }

    /**
     * Método de {@code PlotGraphics} que define o plot {@code plot}
     * @param plot renderização
     */
    public void setPlot(BufferedImage plot) {
        this.plot = plot;
    }

    //    public void mapeamentoX() {
//        pxr = ((x - oix) * (dfx - dix) / (ofx - oix)) + dix;
//    }
//
//    public void mapeamentoY() {
//        pyr = ((y - ofy) * (diy - dfy) / (oiy - ofy)) + dfy;
//    }

//    public void eixoXY() {
//        porcentagem = 0.1;
//
//        render.setColor(Color.BLACK);
//        render.drawLine((int) x0 + 75, (int) y1 - (int) margem(), (int) x1, (int) y1 - (int) margem()); // eixo x
//        render.drawLine((int) x0 + (int) margem(), (int) y0, (int) x0 + (int) margem(), (int) y1 - 75); // eixo Y
//
//    }

//    public void criaLinhasPontos() {
//        pontos = new ArrayList<>();
////        valoresSaida();
//        System.out.println(valoresSaida());
//
//        porcentagem = 0.1;
//
//        oix = 0;
//        ofx = auxList.size();
//        dix = (int) (x0 + margem() + 1);
//        dfx = x1;
//
//        oiy = getMaxValue();
//        ofy = getMinValue();
//        diy = y0;
//        dfy = (int) (y1 - margem());
//
//        for (int i = 0; i < auxList.size(); i++) {
//            x = i;
//            mapeamentoX();
//
//            // System.out.println(Prx);
//            for (int j = 0; j < auxList.size(); j++) {
//                y = auxList.get(i);
//                mapeamentoY();
//                pontos.add(new Point((int) pxr, (int) pyr));
//            }
//        }
//
//        for (int a = 0; a < pontos.size() - 1; a++) {
//            render.setColor(Color.blue);
//            render.drawLine(pontos.get(a).x, pontos.get(a).y, pontos.get(a + 1).x, pontos.get(a + 1).y);
//        }
//    }

//    private void criaPontos() {
//
//        porcentagem = 0.1;
//
//        oix = 0;
//        ofx = auxList.size();
//        dix = (int) (x0 + margem());
//        dfx = x1;
//
//        oiy = getMaxValue();
//        ofy = getMinValue();
//        diy = y0;
//        dfy = (int) (y1 - margem());
//
//        for (int i = 0; i < auxList.size(); i++) {
//            x = i;
//            mapeamentoX();
//
//
//            for (int j = 0; j < auxList.size(); j++) {
//                y = auxList.get(i);
//                mapeamentoY();
//
//                render.setColor(Color.RED);
//                render.fillOval((int) pxr - 4, (int) pyr - 4, 8, 8);
//            }
//        }
//    }

//    /**
//     *
//     */
//    private void criaGrade() {
//
//        Stroke oldStroke = render.getStroke();
//        float[] dash = {2f, 0f, 2f};
//        BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
//        render.setStroke(bs);
//
//        porcentagem = 0.1;
//
//        oix = 0;
//        ofx = auxList.size() / 2;
//        dix = (int) (x0 + margem());
//        dfx = x1;
//
//        oiy = getMaxValue();
//        ofy = getMinValue();
//        diy = y0;
//        dfy = (int) (y1 - margem());
//
//
//        double auxFixa = (double) auxList.size() / ofx;
//        w = auxFixa;
//
//        //X
//        x = 0;
//        for (int i = 0; i < auxList.size(); i++) {
//            mapeamentoX();
//            for (int a = 0; a < auxList.size(); a++) {
//                render.setColor(Color.lightGray);
//                render.drawLine((int) pxr, (int) (y1 - margem()), (int) pxr, (int) y0);
//
//                if ((double) auxList.size() % ofx == 0) {
//                    xLabel = Math.round(w) + "";
//                } else {
//                    xLabel = Math.round(w * 100.0) / 100.0 + "";
//                }
//                FontMetrics metrics = render.getFontMetrics();
//                int labelWidthx = metrics.stringWidth(xLabel);
//
//                render.setColor(Color.BLACK);
//                render.drawString(xLabel, (int) pxr - labelWidthx / 2, (int) y1 - (int) margem() + metrics.getHeight() + 5);
//            }
//            w = w + auxFixa;
//            x++;
//        }
//
//        //Y
//        y = getMinValue();
//        //y = 1;
//        for (int j = 0; j < 11; j++) {
//
//            mapeamentoY();
//            for (int i = 0; i < 11; i++) {
//                render.setColor(Color.lightGray);
//                render.drawLine((int) (x0 + margem()), (int) pyr, (int) x1, (int) pyr);
//
//                yLabel = Math.round(y * 100.00) / 100.00 + "";
//
//                FontMetrics metrics = render.getFontMetrics();
//                int labelWidthy = metrics.stringWidth(yLabel);
//
//                render.setColor(Color.BLACK);
//                render.drawString(yLabel, (int) x0 + (int) margem() - labelWidthy - 10, (int) pyr + metrics.getHeight() / 2);
//            }
//            z++;
//            y += ((getMaxValue() - getMinValue()) / 10);
//            //y++;
//        }
//        render.setStroke(oldStroke);
//    }
//
//    public void saveImage(String nameGraph) {
//        try {
//            ImageIO.write(report, "PNG", new File(nameGraph + ".png"));
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }

//    public void criaImagem(String nameGraph) {
//        valoresSaida();
//        background();
//        whiteBrackground();
//        criaGrade();
//        eixoXY();
//        criaLinhasPontos();
//        criaPontos();
//        saveImage(nameGraph);
//    }

    /**
     * A classe {@code Universe} é responsável por criar os limites virtuais do domínio da plotagem
     * de {@link PlotGraphics}
     *
     * <p>
     *     Armazena as informações dos limites iniciais e finais tanto do eixo X como do eixo Y, além
     *     de realizar o mapeamento de pontos neste domínio, para pixels no {@link PlotGraphics}
     * </p>
     */
    class Universe{
        double xini, xend, yini,yend;
        PlotGraphics plot;
        //TODO Implementar todos os métodos do universo
        public Universe(double xini, double xend, double yini, double yend){
            this.xini = xini;
            this.xend = xend;
            this.yini = yini;
            this.yend = yend;
        }

        /**
         * Método de {@code Universe} que mapeia um valor em x para uma coordenada horizontal da imagem
         * @param vx Valor no eixo X de {@code Universe}
         * @return A coordenada horizontal do pixel equivalente ao {@code Universe} mapeado na imagem
         */
        public int mapX(double vx){
            int result = 0;
            return result;
        }

        /**
         * Método de {@code Universe} que mapeia um valor em y para uma coordenada vertical da imagem
         * @param vy Valor no eixo Y de {@code Universe}
         * @return A coordenada vertical do pixel equivalente ao {@code Universe} mapeado na imagem
         */
        public int mapY(double vy){
            int result = 0;
            return result;
        }

        /**
         * Método de {@code Universe} que mapeia uma coordenada horizontal da imagem x para uma coordenada horizontal no {@code Universe}.
         * @param x a coordenada do pixel x da imagem
         * @return o valor equivalente no {@code Universe}
         */
        public double inverseMapX(int x){
            double result = 0;
            return result;
        }

        /**
         * Método de {@code Universe} que mapeia uma coordenada vertical da imagem y para uma coordenada vertical no {@code Universe}.
         * @param y a coordenada do pixel y da imagem
         * @return o valor vertical equivalente no {@code Universe}
         */
        public double inverseMapY(int y){
            double result = 0;
            return result;
        }

        private double map(double v, double oi, double of, double di, double df){
            //TODO fazer o mapeamento do universo
            double result = 0;
            return result;
        }
    }
}