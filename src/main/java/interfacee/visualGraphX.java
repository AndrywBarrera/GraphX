/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfacee;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 *
 * @author andry
 */
public class visualGraphX extends JFrame {

    // Variables para almacenar los vértices seleccionados
    private static String selectedVertexStart = null;
    private static String selectedVertexEnd = null;

    public visualGraphX() {
        // Creación del grafo
        Graph<String, DefaultWeightedEdge> cityGraph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        // Agregar vértices
        String[] vertices = new String[20];
        for (int i = 0; i < 20; i++) {
            vertices[i] = "N" + (i + 1);
            cityGraph.addVertex(vertices[i]);
        }

        // Agregar aristas con pesos
        cityGraph.setEdgeWeight(cityGraph.addEdge("N1", "N2"), 5);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N2", "N3"), 3);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N3", "N4"), 4);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N4", "N5"), 6);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N1", "N6"), 2);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N6", "N7"), 7);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N7", "N8"), 9);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N8", "N9"), 4);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N9", "N10"), 1);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N10", "N11"), 8);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N11", "N12"), 5);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N12", "N13"), 3);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N13", "N14"), 6);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N14", "N15"), 2);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N15", "N16"), 7);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N16", "N17"), 9);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N17", "N18"), 4);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N18", "N19"), 1);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N19", "N20"), 8);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N1", "N20"), 7);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N20", "N1"), 7);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N17", "N10"), 9);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N6", "N14"), 7);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N15", "N3"), 8);
        cityGraph.setEdgeWeight(cityGraph.addEdge("N3", "N15"), 8);


        // Crear el grafo para visualización con JGraphX
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        Map<String, Object> vertexMap = new HashMap<>();
        graph.getModel().beginUpdate();
        try {
            // Agregar vértices en forma de círculo para visualización
            double centerX = 300, centerY = 300, radius = 250;
            for (int i = 0; i < 20; i++) {
                double angle = 2 * Math.PI * i / 20;
                double x = centerX + radius * Math.cos(angle);
                double y = centerY + radius * Math.sin(angle);

                Object vertex = graph.insertVertex(parent, vertices[i], vertices[i], x, y, 50, 30,
                        "fillColor=#FFA500;strokeColor=#000000;strokeWidth=2;fontColor=#000000;");
                vertexMap.put(vertices[i], vertex);
            }
            // Agregar aristas con pesos
            for (DefaultWeightedEdge edge : cityGraph.edgeSet()) {
                String source = cityGraph.getEdgeSource(edge);
                String target = cityGraph.getEdgeTarget(edge);
                double weight = cityGraph.getEdgeWeight(edge);

                Object vertexSource = vertexMap.get(source);
                Object vertexTarget = vertexMap.get(target);
                graph.insertEdge(parent, null, String.valueOf((int) weight), vertexSource, vertexTarget, "fontColor=#FFA500;fontSize=14;fontStyle=0;strokeColor=#000000;");
            }
        } finally {
            graph.getModel().endUpdate();
        }
        graph.setCellsMovable(false);
        graph.setCellsEditable(false);
        graph.setCellsDisconnectable(false);
        graph.setCellsResizable(false);
        graph.setConnectableEdges(false);
        // Configurar el componente de visualización
        mxGraphComponent graphComponent = new mxGraphComponent(graph);

        // Crear la etiqueta para mostrar información
        JLabel infoLabel = new JLabel("Seleccione dos nodos para calcular la ruta más corta.");
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Escuchar clics en el grafo
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());
                if (cell != null) {
                    String vertex = graph.getLabel(cell);
                    if (cityGraph.containsVertex(vertex)) {
                        if (selectedVertexStart == null) {
                            selectedVertexStart = vertex;

                            infoLabel.setText("Inicio seleccionado: " + selectedVertexStart);
                        } else {
                            selectedVertexEnd = vertex;
                            if (selectedVertexEnd == selectedVertexStart) {
                                selectedVertexEnd = null;
                                return;
                            }

                            infoLabel.setText("Destino seleccionado: " + selectedVertexEnd);
                            if (selectedVertexStart != null && selectedVertexEnd != null) {
                                String date = "";
                                DijkstraShortestPath<String, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(cityGraph);
                                try {
                                    List<DefaultWeightedEdge> path = dijkstra.getPath(selectedVertexStart, selectedVertexEnd).getEdgeList();

                                    date = "" + path;
                                    date = date.replace('[', ' ');
                                    date = date.replace(']', ' ');
                                    date = date.replaceAll(",", "→");
                                    date = date.replaceAll("\\(", "");
                                    date = date.replaceAll("\\)", "");
                                    date = date.replaceAll(":", "a");
                                    date = "Ruta: " + date;

                                } catch (NullPointerException p) {
                                    date = "No existe Ruta";
                                }
                                JOptionPane.showOptionDialog(null, date, "Resultado", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Confirmado"}, 1);
                                selectedVertexStart = null;
                                selectedVertexEnd = null;
                            }
                        }
                    }
                }
            }
        });

        // Crear el botón para cerrar
        JButton closeButton = new JButton("X");
        closeButton.setBounds(600, 0, 50, 30);
        infoLabel.setBounds(0, 0, 600, 30);
        closeButton.addActionListener(e -> System.exit(0));
        graphComponent.setSize(650, 500);
        // Configurar el frame

        setUndecorated(true);
        setLayout(new BorderLayout());
        add(graphComponent, BorderLayout.CENTER);

        // Panel para cerrar
        JPanel topPanel = new JPanel(null);
        topPanel.setPreferredSize(new Dimension(650, 30));
        topPanel.add(closeButton);
        topPanel.add(infoLabel);
        add(topPanel, BorderLayout.NORTH);

        setSize(650, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
