package report;

import kernel.*;

import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Topology {
    int w, h;
    NeuralNetwork nn;
    ArrayList<Node> nodes;
    ArrayList<Line> lines;

    public Topology(NeuralNetwork nn) throws Exception {
        this.nn = nn;
        nodes = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        if(nn.checkIntegrity())
            nnToTopology(1920, 1080);
        else
            throw new Exception("Invalid Network State");
    }

    private void nnToTopology(int width, int height) {
        int xOffset, yOffset;
        Input input = nn.getInput();
        int qtdOfLayers = nn.getLayers().size()+2;
        int qtdInput = input.getValues().size();
        int diameter = Math.min(height/qtdInput-2,100);
        float spacer = (height-(qtdInput*diameter))/(float)(qtdInput+1);

        xOffset = diameter/2;
        yOffset = Math.round(spacer + diameter/2.0f);
        for(double d : input.getValues()){
            Node n = new Node(Color.CYAN, xOffset,yOffset, diameter);
            n.setValue(d);
            nodes.add(n);
            yOffset+= spacer + diameter;
        }
        yOffset = diameter/2;
        xOffset += width/qtdOfLayers;
        for(Layer l : nn.getLayers()){
            diameter = Math.min(100,height/l.getNeuronsCount())-2;
            for(Neuron n : l.getNeurons()){
                Node node = new Node(Color.orange, xOffset, yOffset, diameter);
                node.setValue(n.getValue());
                nodes.add(node);
                yOffset += diameter+2;
            }
            xOffset += width/qtdOfLayers;
            yOffset = diameter/2;
        }

        draw(width, height);
    }
    private void draw(int w, int h){
        BufferedImage img = new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
        Graphics2D render = (Graphics2D) img.getGraphics();
        for(Node n : nodes){
            render.setColor(n.getC());
            render.fillOval(n.x, n.y, n.diameter, n.diameter);
        }
        for(Line l : lines){
            render.setColor(l.getC());
            render.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
        }
        try {
            ImageIO.write(img, "PNG", new File("topology.png"));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    class Node extends Neuron {
        Color c;
        int x,y;
        int diameter;

        public Node(Color c, int x, int y, int diameter) {
            this.c = c;
            this.x = x;
            this.y = y;
            this.diameter = diameter;

        }

        public void setValue(double value){
            this.value = value;
        }

        public Color getC() {
            return c;
        }

        public void setC(Color c) {
            this.c = c;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getDiameter() {
            return diameter;
        }

        public void setDiameter(int diameter) {
            this.diameter = diameter;
        }


    }

    class Line extends Connection {
        Point a, b;
        Color c;

        public Line(Node fromNode, Node toNode,  Color c) {
            this.a = new Point(fromNode.x+fromNode.diameter/2, fromNode.y);
            this.b = new Point(toNode.x-toNode.diameter/2,toNode.y );
            this.c = c;
        }

        public Line(Point a, Point b, Color c){
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public Point getA() {
            return a;
        }

        public void setA(Point a) {
            this.a = a;
        }

        public Point getB() {
            return b;
        }

        public void setB(Point b) {
            this.b = b;
        }

        public Color getC() {
            return c;
        }

        public void setC(Color c) {
            this.c = c;
        }
    }
}
