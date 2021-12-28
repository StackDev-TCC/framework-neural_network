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
 * <p style="color:red">Só funciona à partir da versão 14 do Java, pois faz uso de classes {@code Records}.</p>
 */
public class Topology {
    int w, h;
    static NeuralNetwork nn;
    static ArrayList<Node> nodes;
    static ArrayList<Line> lines;

    public static void createTopology(NeuralNetwork nn, int width, int height) throws Exception {
        Topology.nn = nn;
        nodes = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        if (nn.checkIntegrity())
            renderTopology(width, height);
        else
            throw new Exception("Invalid Network State");
    }

    public static void createTopology(NeuralNetwork nn) throws Exception {
        createTopology(nn, 1920, 1080);
    }

    private static void renderTopology(int width, int height) {
        allocateElements(width, height);
        draw(width, height);
    }

    private static void allocateElements(int width, int height) {

        // Inserindo as duas camadas de Input (data e layer)

        int xOffset, yOffset;
        Input input = nn.getInput();
        int qtdOfLayers = nn.getLayers().size() + 2;
        int qtdInput = input.getValues().size();
        int diameter = Math.min(height / qtdInput - 2, 100);
        float spacer = (height - (qtdInput * diameter)) / (float) (qtdInput + 1);

        xOffset = diameter / 2;
        yOffset = Math.round(spacer + diameter / 2.0f);
        for (double d : input.getValues()) {
            Node n = new Node(Color.CYAN, xOffset, yOffset, diameter,d);

            nodes.add(n);
            yOffset += spacer + diameter;
        }

        //Inserindo as camadas intermediárias

        yOffset = diameter / 2;
        xOffset += width / qtdOfLayers;
        for (Layer l : nn.getLayers()) {
            diameter = Math.min(100, height / l.getNeuronsCount()) - 2;
            for (Neuron n : l.getNeurons()) {
                Node node = new Node(Color.orange, xOffset, yOffset, diameter,n.getValue());
                nodes.add(node);
                yOffset += diameter + 2;
            }
            xOffset += width / qtdOfLayers;
            yOffset = diameter / 2;
        }

        //Inserindo Output
        draw(width, height);
    }

    private static void draw(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D render = (Graphics2D) img.getGraphics();

        render.setColor(Color.white);
        render.fillRect(0,0,w,h);

        for (Node n : nodes) {
            render.setColor(n.c());
            render.fillOval(n.x(), n.y(), n.diameter(), n.diameter());
        }
        for (Line l : lines) {
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

record Node(Color c, int x, int y, int diameter, double value){
}

record Line(Color c, Point a, Point b, double w) {
    public Line(Color c, Node fromNode, Node toNode, double w) {
        this(c, new Point(fromNode.x()+fromNode.diameter()/2, fromNode.y()),
             new Point(toNode.x()-toNode.diameter()/2,toNode.y()),w);
    }
}
