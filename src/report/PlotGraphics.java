package report;


import Utils.PixelCalc;
import operations.TrainingNetwork;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private String xLabel;
    private String yLabel;
    private String title;
    private float margin;

    public static void main(String[] args){
        double[] data = new double[]{400, 401, 398, 396, 394, 380, 370, 340, 345, 340, 320, 305, 250, 140, 100, 98, 95,
                94, 92, 90, 89, 86, 84, 81, 70, 59, 57, 55, 55, 53, 51, 50, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28,
                27, 26, 25, 24, 23, 22, 21, 20, 18, 16, 14, 10, 9, 8, 7,6, 5, 4, 3, 2, 1, 0, 0, 0, 0};
        PlotGraphics pg = new PlotGraphics(800,600,90,"eixoX", "eixoY","Gráfico", data);
        pg.plot();
        JFrame f = new JFrame("Graphic");
        f.setBounds(10,10,pg.getW()+4,pg.getH()+15);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel l = new JLabel();
        l.setIcon(new ImageIcon(pg.getPlot()));
        f.add(l);
        f.setVisible(true);
        pg.saveImage("test");
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

    private int calculateBorder(){
        int max = Math.max(getW(), getH());
        float p = margin/100.0f;
        return Math.round(max*(1.0f-p));
    }

    public void plot(){
        int border = calculateBorder();
        Graphics2D render = (Graphics2D) plot.getGraphics();
        drawBackground(border, render);
        drawGrid(border, render);
        drawTitle(border, render);
        drawAxis(border, render);
        plotData(border, render);
    }

    private void drawBackground(int border, Graphics2D render){
        render.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        render.setColor(Color.lightGray);
        render.fillRect(border, border, w-2*border, h-2*border);
    }

    private void drawGrid(int border, Graphics2D render){
        //salva a linha no estilo continuo
        Stroke oldStroke = render.getStroke();
        //muda o estilo da linha para pontilhada
        float[] dash = {2f, 0f, 2f};
        BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        render.setStroke(bs);

        int tamH = h-border*2;
        int incH = tamH/20;

        int tamW = w-border*2;
        int incW = tamW/10;

        double sizex = u.xend - u.xini;
        double incx = sizex/10.0;
        double x = u.xini;

        double sizey = u.yend - u.yini;
        double incy = sizey/20.0;
        double y = u.yend;

        for(int j = 0; j <= tamH; j+=incH, y-=incy){
            render.setColor(Color.darkGray);
            render.drawLine(border, border+j, w-border, border+j);

            render.setColor(Color.BLACK);
            FontMetrics metrics = render.getFontMetrics();
            yLabel = Math.round(y) + "";
            int labelWidthy = metrics.stringWidth(yLabel);
            render.drawString(yLabel,  border - labelWidthy - 10,  border + j + 5);
        }

        for(int i = 0; i <= tamW; i+=incW, x+=incx){
            render.setColor(Color.darkGray);
            render.drawLine(border + i, border , border + i, h - border);

            render.setColor(Color.BLACK);
            FontMetrics metrics = render.getFontMetrics();
            xLabel = Math.round(x) + "";
            int labelWidthx = metrics.stringWidth(xLabel);
            render.drawString(xLabel, border + i - labelWidthx/2, h - border + 20);
        }
        //retorna a linha para o estilo continuo
        render.setStroke(oldStroke);
    }

    private void drawTitle(int border, Graphics2D render){
        render.setColor(Color.BLACK);
        render.drawString(title, w/2 - (render.getFontMetrics().stringWidth(title)/2) ,  calculateBorder()-20);
    }

    private void drawAxis(int border, Graphics2D render){

        render.setColor(Color.BLACK);
        render.drawLine(border, h-border, w-border, h-border); // eixo x
        render.drawLine(border, h-border, border, border); // eixo Y
    }

    private void plotData(int border, Graphics2D render){

        int bulletDiameter = Math.round(Math.min(w , h) * 0.01f);
        int prevX = u.mapX(0);
        int prevY = u.mapY(data[0]);

        for (int i = 1; i < data.length; i++){
            int x = u.mapX(i);
            int y = u.mapY(data[i]);

            render.setColor(Color.BLUE);
            render.drawLine(prevX,prevY,x,y);
            render.setColor(Color.RED);
            render.fillOval(x - bulletDiameter/2,  y - bulletDiameter/2, bulletDiameter, bulletDiameter);
            prevX = x;
            prevY = y;
        }

        for (int i = 0; i < data.length; i++){
            int x = u.mapX(i);
            int y = u.mapY(data[i]);

            render.setColor(Color.RED);
            render.fillOval(x - bulletDiameter/2,  y - bulletDiameter/2, bulletDiameter, bulletDiameter);
        }
    }

    private Universe generateU(){
        if(data != null) {
            double xmin = 0;
            double xmax = data.length-1;
            double ymin = getMinValue();
            double ymax = getMaxValue();
            double tmp = ymax-ymin;
            ymax+= tmp * 0.1;
            ymin-= tmp * 0;
            return new Universe(xmin, xmax, ymin, ymax, this);
        }
        return null;
    }

    /**
     * Construtor de {@code PlotGraphics} com todos os parâmetros necessários para a plotagem de um gráfico.
     * <p>Uma atenção especial ao parâmetro {@code margin}, pois este será usado como uma escala de porcentagem
     * da imagem (de 0 a 100%) para o cálculo da margem de plotagem. Uma margin de 100 plota o gráfico utilizando
     * toda a área da imagem, sem bordas. Valores menores vão criar bordas maiores. O ideal para a maioria dos casos são
     * valores acima de 85%</p>
     *
     * @param w A largura da Imagem geradora do gráfico.
     * @param h A altura da Imagem geradora do gráfico.
     * @param margin A porcentagem da imagem (de 0 a 100) que estará dentro da margem para plotar o gráfico.
     * @param xLabel O rótulo do eixo X.
     * @param yLabel O rótulo do eixo Y.
     * @param title O titulo do gráfico.
     * @param data O {@code Array} de elementos a serem plotados.
     */
    public PlotGraphics(int w, int h, float margin, String xLabel, String yLabel, String title, double[] data){
        this.w = w;
        this.h = h;
        this.margin = margin;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.title = title;
        this.data = data;
        startImage();
        this.u = generateU();
    }

    /**
     * Construtor de {@code PlotGraphics} com os parâmetros iniciais para a criação do objeto
     * antes de se ter calculado os dados para serem plotados
     * @param w A largura da Imagem geradora do gráfico.
     * @param h A altura da Imagem geradora do gráfico.
     * @param margin A porcentagem da imagem (de 0 a 100) que estará dentro da margem para plotar o gráfico
     * @param xLabel O rótulo do eixo X.
     * @param yLabel O rótulo do eixo Y.
     * @param title O titulo do gráfico.
     */
    public PlotGraphics(int w, int h, float margin, String xLabel, String yLabel, String title){
        this.w = w;
        this.h = h;
        this.margin = margin;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.title = title;
        startImage();
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
        startImage();
    }

    /**
     * Método de {@code PlotGraphics}. Carrega uma nova BufferedImage em plot e pinta tudo de branco
     */
    private void startImage(){
        plot = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        int white = Color.white.getRGB();
        for(int j = 0; j < h; j++)
            for(int i = 0; i < w; i++)
                plot.setRGB(i,j,white);
    }

    /**
     * Método de {@code PlotGraphics} que insere um novo {@code Array} para ser dimensionado e plotado.
     * @param data o {@code Array} para ser plotado.
     */
    public void setData(double[] data){
        this.data = data;
        this.u = generateU();
    }

    /**
     * Método de {@code PlotGraphics} que permite a definição do titulo da plotagem da imagem de forma parametrizada
     * @param title titulo do gráfico
     */
    public void setTitle(String title) {
        this.title = title;
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
        return (Graphics2D) plot.getGraphics();
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

    /**
     * Método de {@code PlotGraphics} que salva a imagem criada e adiciona .PNG ao arquivo
     * @param nameGraphic nome do arquivo
     */
    public void saveImage(String nameGraphic) {
        try {
            ImageIO.write(plot, "PNG", new File(nameGraphic + ".png"));
        } catch (IOException ioe) {
        }
    }

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
        //Sistema de coordenadas do universo
        double xini, xend, yini,yend;
        //Sistema de coordenadas da tela (pixels)
        int pxini, pxend, pyini, pyend;
        PlotGraphics plot;
        //TODO Implementar todos os métodos do universo

        public Universe(double xini, double xend, double yini, double yend, PlotGraphics plot){
            this.xini = xini;
            this.xend = xend;
            this.yini = yini;
            this.yend = yend;
            this.plot = plot;
            calculateLimits();
        }
        private void calculateLimits(){
            int border = plot.calculateBorder();
            pxini = border;
            pxend = plot.getW()-border;
            pyini = border;
            pyend = plot.getH()-border;
        }
        /**
         * Método de {@code Universe} que mapeia um valor em x para uma coordenada horizontal da imagem
         * @param vx Valor no eixo X de {@code Universe}
         * @return A coordenada horizontal do pixel equivalente ao {@code Universe} mapeado na imagem
         */
        public int mapX(double vx){
            return (int)Math.round(map(vx,xini,xend,pxini, pxend));
        }

        /**
         * Método de {@code Universe} que mapeia um valor em y para uma coordenada vertical da imagem
         * @param vy Valor no eixo Y de {@code Universe}
         * @return A coordenada vertical do pixel equivalente ao {@code Universe} mapeado na imagem
         */
        public int mapY(double vy){
            return (int)Math.round(map(vy, yini, yend, pyend, pyini));
        }

        /**
         * Método de {@code Universe} que mapeia uma coordenada horizontal da imagem x para uma coordenada horizontal no {@code Universe}.
         * @param x a coordenada do pixel x da imagem
         * @return o valor equivalente no {@code Universe}
         */
        public double inverseMapX(int x){
            return (map(x, pxini, pxend, xini, xend));
        }

        /**
         * Método de {@code Universe} que mapeia uma coordenada vertical da imagem y para uma coordenada vertical no {@code Universe}.
         * @param y a coordenada do pixel y da imagem
         * @return o valor vertical equivalente no {@code Universe}
         */
        public double inverseMapY(int y){
            return map(y, pyini, pyend, yend, yini);
        }

        /**
         * Mapeamento geral, dado o valor original, a escala original e a escala de destino a ser mapeada
         * @param v O valor original
         * @param oi Valor inicial da escala de origem
         * @param of Valor final da escala de origem
         * @param di Valor inicial da escala de destino
         * @param df Valor final da escala de destino
         * @return O Valor v mapeado na escala de destino
         */
        private double map(double v, double oi, double of, double di, double df){
            if(of-oi == 0 )
                return Float.NaN;
            return (v-oi)*(df-di)/(of-oi) + di;
        }
    }
}