package report;

import kernel.*;

import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * A classe {@code Topology} renderiza a {@link NeuralNetwork} em uma imagem
 * <p>Esta classe permite, através dos métodos estáticos {@link #createTopology(NeuralNetwork)} e
 * {@link #createTopology(NeuralNetwork, int, int)} uma forma de renderizar a topologia atual da
 * instância da {@link NeuralNetwork}</p>
 *
 * <p style="color:red">Só funciona à partir da versão 16 do Java, pois faz uso de classes {@code Records}.</p>
 */
public class Topology {
    int w, h;
    static NeuralNetwork nn;
    static ArrayList<Line> staticLines;
    static ArrayList<Node> staticNodes;
    static int margin = 50;

    public static void createTopology(NeuralNetwork nn, int width, int height) throws Exception {
        Topology.nn = nn;
        staticLines = new ArrayList<>();
        staticNodes = new ArrayList<>();
        if (nn.checkIntegrity())
            renderTopology(width, height);
        else
            throw new Exception("Invalid Network State");
    }

    public static void createTopology(NeuralNetwork nn) throws Exception {
        createTopology(nn, 1920, 1080);
    }

    private static void renderTopology(int width, int height) {
        //Construindo a disposição dos elementos
        allocateElements(width, height);
        //Desenhando e salvando imagem output
        draw(width, height);
    }

    private static void allocateElements(int width, int height) {

        // Inserindo as camadas com Neurônios

        ArrayList<NLayer> layers = new ArrayList<>();
        int xOffset = margin;
        Color c;
        for(int i = 0; i < nn.getLayersCount(); i++){
            int qtd = nn.getLayers().get(i).getNeuronsCount();
            int diameter = Math.min((height-2*margin)/qtd,100);
            float spacer = ((height-2*margin) - (qtd * diameter)) / (float) (qtd + 1);
            if(i==0 && nn.getInput()!=null)
                c = Color.blue;
            else if(i == nn.getLayersCount()-1 && nn.getOutput()!=null)
                c = Color.green;
            else
                c = Color.orange;
            //Inserindo os neurônios para cada camada
            ArrayList<Node> nodes = new ArrayList<>();
            int yOffset = Math.round(margin+spacer);
            for(int node = 0; node < qtd; node++){
                double v = nn.getLayers().get(i).getNeurons().get(node).getOutput();
                Node n = new Node(c, xOffset, yOffset, diameter, v);
                nodes.add(n);
                staticNodes.add(n);
                yOffset+=spacer+diameter;
            }
            NLayer l = new NLayer(xOffset+diameter/2, spacer, diameter, nodes);
            layers.add(l);
            xOffset += (width-2*margin)/nn.getLayersCount();
        }

        //Adicionando as linhas de conexões entre uma camada e outra
        if(layers.size()>0) {
            NLayer prev = layers.get(0);
            for (int i = 1; i < layers.size(); i++) {
                NLayer next = layers.get(i);
                for (Node from : prev.nodes()) {
                    for (Node to : next.nodes()) {
                        Line l = new Line(Color.BLACK, from, to);
                        staticLines.add(l);
                    }
                }
                prev = next;
            }
        }
    }

    private static void draw(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D render = (Graphics2D) img.getGraphics();
        render.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        render.setBackground(Color.white);
        render.clearRect(0,0,w,h);

        for (Node n : staticNodes) {
            render.setColor(n.c());
            render.fillOval(n.x()-n.diameter()/2, n.y()-n.diameter()/2, n.diameter(), n.diameter());
            render.setColor(n.c().darker());
            render.drawOval(n.x()-n.diameter()/2,n.y()-n.diameter()/2, n.diameter(),n.diameter());
            render.setColor(Color.white);
            String v = n.value()+"";
            int fw = render.getFontMetrics().stringWidth(v);
            int fh = render.getFontMetrics().getHeight();
            render.drawString(n.value()+"",n.x()-fw/2,n.y()+fh/2);
        }
        for (Line l : staticLines) {
            render.setColor(l.c());
            render.drawLine(l.a().x, l.a().y, l.b().x, l.b().y);
        }
        try {
            ImageIO.write(img, "PNG", new File("topology.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

record Node(Color c, int x, int y, int diameter, double value){}

record Line(Color c, Point a, Point b) {
    public Line(Color c, Node fromNode, Node toNode) {
        this(c, new Point(fromNode.x()+fromNode.diameter()/2, fromNode.y()),
                new Point(toNode.x()-toNode.diameter()/2,toNode.y()));
    }
}

record NLayer(int x, float vSpacer, int diameter, ArrayList<Node> nodes){}