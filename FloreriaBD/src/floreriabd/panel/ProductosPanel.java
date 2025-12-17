package floreriabd.panel;

import Floreria.Productos;
import FloreriaService.ProductosService;
import floreriabd.Colors;
import floreriabd.EstilosUI;
import floreriabd.FuentePersonalizada;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class ProductosPanel extends JPanel {
    
    private JLabel fondo;
    private JButton btnCargarArchivo;
    private JButton btnExportarCSV;
    private JButton btnLimpiarFiltros;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private List<Productos> listaProductos;
    private ProductosService productosService;
    private JButton btnEliminarProducto;
    
    private JTextField txtFiltroNombre;
    private JTextField txtFiltroDescripcion;
    private JComboBox<String> comboFiltroEstado;
    private JTextField txtFiltroPrecioMin;
    private JTextField txtFiltroPrecioMax;
    private JTextField txtFiltroStockMin;
    
    public ProductosPanel() {
        productosService = new ProductosService();
        listaProductos = new ArrayList<>();
        paraLabels();
        configurarFiltros();
        configurarTabla();
        configurarBotones();
        cargarProductosDesdeDB();
    }
    
    private void paraLabels() {
       setLayout(null);
        setBackground(Color.WHITE);
        setBounds(0, 0, 1220, 570);

        fondo = new JLabel();
        fondo.setBounds(0, 0, 1220, 600);
        fondo.setLayout(null);
        add(fondo);
    }
    
    private void configurarFiltros() {
        JLabel lblFiltros = new JLabel("Filtros:");
        lblFiltros.setBounds(50, 20, 100, 25);
        lblFiltros.setFont(FuentePersonalizada.cargarMontserrat(14f));
        add(lblFiltros);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 50, 80, 25);
        lblNombre.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblNombre);
        
        txtFiltroNombre = new JTextField();
        txtFiltroNombre.setBounds(130, 50, 150, 25);
        txtFiltroNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });
        add(txtFiltroNombre);
        
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(300, 50, 80, 25);
        lblDescripcion.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblDescripcion);
        
        txtFiltroDescripcion = new JTextField();
        txtFiltroDescripcion.setBounds(380, 50, 150, 25);
        txtFiltroDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });
        add(txtFiltroDescripcion);
        
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(550, 50, 60, 25);
        lblEstado.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblEstado);
        
        comboFiltroEstado = new JComboBox<>(new String[]{"Todos", "Activo", "Inactivo"});
        comboFiltroEstado.setBounds(610, 50, 100, 25);
        comboFiltroEstado.addActionListener(e -> aplicarFiltros());
        add(comboFiltroEstado);
        
        JLabel lblPrecioMin = new JLabel("Precio Min:");
        lblPrecioMin.setBounds(50, 80, 80, 25);
        lblPrecioMin.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblPrecioMin);
        
        txtFiltroPrecioMin = new JTextField();
        txtFiltroPrecioMin.setBounds(130, 80, 80, 25);
        txtFiltroPrecioMin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });
        add(txtFiltroPrecioMin);
        
        JLabel lblPrecioMax = new JLabel("Precio Max:");
        lblPrecioMax.setBounds(220, 80, 80, 25);
        lblPrecioMax.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblPrecioMax);
        
        txtFiltroPrecioMax = new JTextField();
        txtFiltroPrecioMax.setBounds(300, 80, 80, 25);
        txtFiltroPrecioMax.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });
        add(txtFiltroPrecioMax);
        
        JLabel lblStockMin = new JLabel("Stock Min:");
        lblStockMin.setBounds(400, 80, 80, 25);
        lblStockMin.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblStockMin);
        
        txtFiltroStockMin = new JTextField();
        txtFiltroStockMin.setBounds(480, 80, 80, 25);
        txtFiltroStockMin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });
        add(txtFiltroStockMin);
        
        btnLimpiarFiltros = new JButton("Limpiar Filtros");
        btnLimpiarFiltros.setBounds(580, 80, 130, 25);
        btnLimpiarFiltros.setBackground(Colors.VERDE_CLARO);
        btnLimpiarFiltros.setForeground(Color.WHITE);
        btnLimpiarFiltros.setFont(FuentePersonalizada.cargarMontserrat(10f));
        btnLimpiarFiltros.addActionListener(e -> limpiarFiltros());
        add(btnLimpiarFiltros);
    }
    
    private void configurarBotones() {
        btnCargarArchivo = new JButton("Cargar Archivo CSV");
        btnCargarArchivo.setBounds(50, 120, 180, 40);
        btnCargarArchivo.setBackground(Colors.VERDE_CLARO);
        btnCargarArchivo.setForeground(Color.WHITE);
        btnCargarArchivo.setFont(FuentePersonalizada.cargarMontserrat(14f));
        
        btnCargarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivoCSV();
            }
        });
        
        btnExportarCSV = new JButton("Exportar CSV");
        btnExportarCSV.setBounds(250, 120, 150, 40);
        btnExportarCSV.setBackground(Colors.VERDE_CLARO);
        btnExportarCSV.setForeground(Color.WHITE);
        btnExportarCSV.setFont(FuentePersonalizada.cargarMontserrat(14f));
        
        btnExportarCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarCSV();
            }
        });
        
        add(btnCargarArchivo);
        add(btnExportarCSV);
        
        btnEliminarProducto = new JButton("Eliminar Producto");
        btnEliminarProducto.setBounds(420, 120, 200, 40);
        btnEliminarProducto.setBackground(Colors.VERDE_FUERTE);
        btnEliminarProducto.setForeground(Color.WHITE);
        btnEliminarProducto.setFont(FuentePersonalizada.cargarMontserrat(14f));

        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProductoSeleccionado();
            }
        });

        add(btnEliminarProducto);
    }
    
    private void configurarTabla() {
        String[] columnNames = {"ID", "Nombre", "Descripción", "Precio", "Stock", "Estado", "ID Categoría"};

        modeloTabla = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // El ID no es editable pq se rompe la bd ñekekeke
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0, 4, 6 -> Integer.class;
                    case 3 -> BigDecimal.class;
                    default -> String.class;
                };
            }
        };

        modeloTabla.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getFirstRow() >= 0 && e.getColumn() >= 0) {
                actualizarProductoDesdeTabla(e.getFirstRow(), e.getColumn());
            }
        });

        tablaProductos = new JTable(modeloTabla);

        tablaProductos.setFont(FuentePersonalizada.cargarMontserrat((float) 14));
        tablaProductos.setRowHeight(25);
        tablaProductos.setShowGrid(false);
        tablaProductos.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = tablaProductos.getTableHeader();
        header.setFont(FuentePersonalizada.cargarMontserrat((float) 16));
        header.setBackground(Colors.VERDE_CLARO);
        header.setForeground(Color.WHITE);


        tablaProductos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 255, 250) : Color.WHITE);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(new Color(0, 120, 215));
                    c.setForeground(Color.WHITE);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return c;
            }
        });

        sorter = new TableRowSorter<>(modeloTabla);
        tablaProductos.setRowSorter(sorter);

        configurarEditoresColumnas();

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBounds(50, 180, 1100, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));

        add(scrollPane);
    }

    
    private void cargarArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            try {
                List<Productos> productosCSV = leerArchivoCSV(selectedFile);
                int productosAgregados = 0;
                int productosConError = 0;
                
                for (Productos producto : productosCSV) {
                    try {
                        if (productosService.agregarProducto(producto)) {
                            productosAgregados++;
                        } else {
                            productosConError++;
                        }
                    } catch (Exception e) {
                        productosConError++;
                        System.err.println("Error agregando producto " + producto.getNombreProd() + ": " + e.getMessage());
                    }
                }
                
                cargarProductosDesdeDB();
                
                String mensaje = String.format(
                    "Procesamiento completado:\n" +
                    "- Productos agregados exitosamente: %d\n" +
                    "- Productos con errores: %d\n" +
                    "- Total procesados: %d",
                    productosAgregados, productosConError, productosCSV.size()
                );
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Error al procesar el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    private List<Productos> leerArchivoCSV(File file) throws IOException {
        List<Productos> productosCSV = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        String line;
        boolean isFirstLine = true;
        
        while ((line = br.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                if (line.toLowerCase().contains("nombre") || line.toLowerCase().contains("precio")) {
                    continue;
                }
            }
            
            String[] parts = parseCSVLine(line);
            
            if (parts.length >= 7) {
                try {
                    Productos producto = new Productos();
                    
                    int id = Integer.parseInt(parts[0].trim());
                    if (id > 0) {
                        producto.setIdProducto(id);
                    }
                    
                    producto.setNombreProd(parts[1].trim());
                    producto.setDescripProd(parts[2].trim());
                    producto.setPrecioProd(new BigDecimal(parts[3].trim()));
                    producto.setStockDispo(Integer.parseInt(parts[4].trim()));
                    producto.setEstadoProd(parts[5].trim());
                    producto.setIdCategoria(Integer.parseInt(parts[6].trim()));
                    
                    productosCSV.add(producto);
                } catch (Exception e) {
                    System.err.println("Error procesando línea CSV: " + line);
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
        
        br.close();
        return productosCSV;
    }
    
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        
        result.add(field.toString());
        return result.toArray(new String[0]);
    }
    
    private void exportarCSV() {
        if (listaProductos.isEmpty()) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, 
                "No hay datos para exportar.",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");
        fileChooser.setSelectedFile(new File("productos.csv"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Asegurar que el archivo tenga extensión .csv
            if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
            }
            
            try {
                escribirArchivoCSV(selectedFile);
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Archivo exportado exitosamente a: " + selectedFile.getAbsolutePath(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Error al exportar el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    private void escribirArchivoCSV(File file) throws IOException {
        java.io.PrintWriter pw = new java.io.PrintWriter(file);
        
        pw.println("idProducto,nombreProd,descripProd,precioProd,stockDispo,estadoProd,idCategoria");
        
        for (Productos producto : listaProductos) {
            pw.printf("%d,\"%s\",\"%s\",%s,%d,\"%s\",%d%n",
                producto.getIdProducto(),
                producto.getNombreProd(),
                producto.getDescripProd(),
                producto.getPrecioProd().toString(),
                producto.getStockDispo(),
                producto.getEstadoProd(),
                producto.getIdCategoria()
            );
        }
        
        pw.close();
    }
    
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        
        for (Productos producto : listaProductos) {
            Object[] row = {
                producto.getIdProducto(),
                producto.getNombreProd(),
                producto.getDescripProd(),
                producto.getPrecioProd(),
                producto.getStockDispo(),
                producto.getEstadoProd(),
                producto.getIdCategoria()
            };
            modeloTabla.addRow(row);
        }
    }
    
    private void aplicarFiltros() {
        List<RowFilter<Object, Object>> filtros = new ArrayList<>();
        
        String nombre = txtFiltroNombre.getText().trim();
        if (!nombre.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + nombre, 1));
        }
        
        String descripcion = txtFiltroDescripcion.getText().trim();
        if (!descripcion.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)" + descripcion, 2));
        }
        
        String estado = (String) comboFiltroEstado.getSelectedItem();
        if (!estado.equals("Todos")) {
            filtros.add(RowFilter.regexFilter(estado, 5));
        }
        
        String precioMinStr = txtFiltroPrecioMin.getText().trim();
        if (!precioMinStr.isEmpty()) {
            try {
                double precioMin = Double.parseDouble(precioMinStr);
                filtros.add(new RowFilter<Object, Object>() {
                    @Override
                    public boolean include(Entry<? extends Object, ? extends Object> entry) {
                        try {
                            double precio = Double.parseDouble(entry.getStringValue(3));
                            return precio >= precioMin;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                });
            } catch (NumberFormatException e) {
            }
        }
        
        String precioMaxStr = txtFiltroPrecioMax.getText().trim();
        if (!precioMaxStr.isEmpty()) {
            try {
                double precioMax = Double.parseDouble(precioMaxStr);
                filtros.add(new RowFilter<Object, Object>() {
                    @Override
                    public boolean include(Entry<? extends Object, ? extends Object> entry) {
                        try {
                            double precio = Double.parseDouble(entry.getStringValue(3));
                            return precio <= precioMax;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                });
            } catch (NumberFormatException e) {
            }
        }
        
        String stockMinStr = txtFiltroStockMin.getText().trim();
        if (!stockMinStr.isEmpty()) {
            try {
                int stockMin = Integer.parseInt(stockMinStr);
                filtros.add(new RowFilter<Object, Object>() {
                    @Override
                    public boolean include(Entry<? extends Object, ? extends Object> entry) {
                        try {
                            int stock = Integer.parseInt(entry.getStringValue(4));
                            return stock >= stockMin;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                });
            } catch (NumberFormatException e) {
            }
        }
        
        if (filtros.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filtros));
        }
    }
    
    private void limpiarFiltros() {
        txtFiltroNombre.setText("");
        txtFiltroDescripcion.setText("");
        comboFiltroEstado.setSelectedIndex(0);
        txtFiltroPrecioMin.setText("");
        txtFiltroPrecioMax.setText("");
        txtFiltroStockMin.setText("");
        sorter.setRowFilter(null);
    }
    
    public List<Productos> getProductosCargados() {
        return new ArrayList<>(listaProductos);
    }
    
    public void agregarProducto(Productos producto) {
        if (agregarProductoADB(producto)) {
            cargarProductosDesdeDB();
        }
    }
    
    public void limpiarProductos() {
        listaProductos.clear();
        actualizarTabla();
    }
    
    public void cargarDatosEjemplo() {
        listaProductos.clear();
        
        Productos p1 = new Productos();
        p1.setIdProducto(1);
        p1.setNombreProd("Rosa Roja");
        p1.setDescripProd("Rosa roja clásica para regalos románticos");
        p1.setPrecioProd(new BigDecimal("25.50"));
        p1.setStockDispo(100);
        p1.setEstadoProd("Activo");
        p1.setIdCategoria(1);
        
        Productos p2 = new Productos();
        p2.setIdProducto(2);
        p2.setNombreProd("Tulipán Amarillo");
        p2.setDescripProd("Tulipán amarillo vibrante perfecto para primavera");
        p2.setPrecioProd(new BigDecimal("15.75"));
        p2.setStockDispo(50);
        p2.setEstadoProd("Activo");
        p2.setIdCategoria(1);
        
        Productos p3 = new Productos();
        p3.setIdProducto(3);
        p3.setNombreProd("Orquídea Blanca");
        p3.setDescripProd("Elegante orquídea blanca para ocasiones especiales");
        p3.setPrecioProd(new BigDecimal("85.00"));
        p3.setStockDispo(20);
        p3.setEstadoProd("Activo");
        p3.setIdCategoria(2);
        
        listaProductos.add(p1);
        listaProductos.add(p2);
        listaProductos.add(p3);
        
        actualizarTabla();
    }
    
    private void cargarProductosDesdeDB() {
        try {
            List<Productos> productosDB = productosService.obtenerTodosLosProductos();

            if (productosDB != null && !productosDB.isEmpty()) {
                listaProductos.clear();
                listaProductos.addAll(productosDB);
                actualizarTabla();
            } else {
                cargarDatosEjemplo();
            }

        } catch (Exception e) {
            System.err.println("Error cargando productos desde la base de datos: " + e.getMessage());
            e.printStackTrace();
            cargarDatosEjemplo();
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, 
                "No se pudieron cargar los productos desde la base de datos. Se cargaron datos de ejemplo.",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean agregarProductoADB(Productos producto) {
        try {
            boolean resultado = productosService.agregarProducto(producto);
            if (resultado) {
                cargarProductosDesdeDB();
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error agregando producto a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarProductoEnDB(Productos producto) {
        try {
            boolean resultado = productosService.actualizarProducto(producto);
            if (resultado) {
                cargarProductosDesdeDB();
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error actualizando producto en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarProductoDeDB(int idProducto) {
        try {
            boolean resultado = productosService.eliminarProducto(idProducto);
            if (resultado) {
                cargarProductosDesdeDB();
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error eliminando producto de la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void refrescarTablaDesdeDB() {
        cargarProductosDesdeDB();
    }
    private void configurarEditoresColumnas() {
        JComboBox<String> comboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        tablaProductos.getColumnModel().getColumn(5).setCellEditor(new javax.swing.DefaultCellEditor(comboEstado));
    }

    private void actualizarProductoDesdeTabla(int row, int column) {
        try {
            int modelRow = tablaProductos.convertRowIndexToModel(row);
            if (sorter != null) {
                modelRow = sorter.convertRowIndexToModel(row);
            }
            
            Integer idProducto = (Integer) modeloTabla.getValueAt(modelRow, 0);
            
            if (idProducto == null) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Error: No se pudo identificar el producto a actualizar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Productos productoAActualizar = null;
            for (Productos p : listaProductos) {
                if (p.getIdProducto() == idProducto) {
                    productoAActualizar = p;
                    break;
                }
            }
            
            if (productoAActualizar == null) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Error: Producto no encontrado en la lista local.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Object nuevoValor = modeloTabla.getValueAt(modelRow, column);
            boolean valorValido = true;
            String mensajeError = "";
            
            switch (column) {
                case 1: // Nombre
                    String nombre = nuevoValor.toString().trim();
                    if (nombre.isEmpty()) {
                        valorValido = false;
                        mensajeError = "El nombre no puede estar vacío.";
                    } else {
                        productoAActualizar.setNombreProd(nombre);
                    }
                    break;
                    
                case 2: // Descripción
                    String descripcion = nuevoValor.toString().trim();
                    if (descripcion.isEmpty()) {
                        valorValido = false;
                        mensajeError = "La descripción no puede estar vacía.";
                    } else {
                        productoAActualizar.setDescripProd(descripcion);
                    }
                    break;
                    
                case 3: // Precio
                    try {
                        BigDecimal precio = new BigDecimal(nuevoValor.toString());
                        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                            valorValido = false;
                            mensajeError = "El precio debe ser mayor que 0.";
                        } else {
                            productoAActualizar.setPrecioProd(precio);
                        }
                    } catch (NumberFormatException e) {
                        valorValido = false;
                        mensajeError = "El precio debe ser un número válido.";
                    }
                    break;
                    
                case 4: // Stock
                    try {
                        int stock = Integer.parseInt(nuevoValor.toString());
                        if (stock < 0) {
                            valorValido = false;
                            mensajeError = "El stock no puede ser negativo.";
                        } else {
                            productoAActualizar.setStockDispo(stock);
                        }
                    } catch (NumberFormatException e) {
                        valorValido = false;
                        mensajeError = "El stock debe ser un número entero válido.";
                    }
                    break;
                    
                case 5: // Estado
                    String estado = nuevoValor.toString().trim();
                    if (!estado.equals("Activo") && !estado.equals("Inactivo")) {
                        valorValido = false;
                        mensajeError = "El estado debe ser 'Activo' o 'Inactivo'.";
                    } else {
                        productoAActualizar.setEstadoProd(estado);
                    }
                    break;
                    
                case 6: // ID Categoría
                    try {
                        int idCategoria = Integer.parseInt(nuevoValor.toString());
                        if (idCategoria <= 0) {
                            valorValido = false;
                            mensajeError = "El ID de categoría debe ser mayor que 0.";
                        } else {
                            productoAActualizar.setIdCategoria(idCategoria);
                        }
                    } catch (NumberFormatException e) {
                        valorValido = false;
                        mensajeError = "El ID de categoría debe ser un número entero válido.";
                    }
                    break;
            }
            
            if (!valorValido) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, mensajeError, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                refrescarTablaDesdeDB();
                return;
            }
            
            boolean actualizado = productosService.actualizarProducto(productoAActualizar);
            
            if (actualizado) {
                for (int i = 0; i < listaProductos.size(); i++) {
                    if (listaProductos.get(i).getIdProducto() == idProducto) {
                        listaProductos.set(i, productoAActualizar);
                        break;
                    }
                }

                tablaProductos.setSelectionBackground(Color.GREEN);
                tablaProductos.setRowSelectionInterval(row, row);
                
                javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
                    tablaProductos.clearSelection();
                    tablaProductos.setSelectionBackground(javax.swing.UIManager.getColor("Table.selectionBackground"));
                });
                timer.setRepeats(false);
                timer.start();
                
            } else {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Error al actualizar el producto en la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                refrescarTablaDesdeDB();
            }
            
        } catch (Exception e) {
            System.err.println("Error actualizando producto desde tabla: " + e.getMessage());
            e.printStackTrace();
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, 
                "Error inesperado al actualizar el producto: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            refrescarTablaDesdeDB();
        }
    }
    
    private void eliminarProductoSeleccionado() {
        int filaSeleccionada = tablaProductos.getSelectedRow();

        if (filaSeleccionada == -1) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un producto para eliminar.",
                "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tablaProductos.convertRowIndexToModel(filaSeleccionada);
        Integer idProducto = (Integer) modeloTabla.getValueAt(modelRow, 0);
        String nombreProducto = (String) modeloTabla.getValueAt(modelRow, 1);

        EstilosUI.aplicarEstiloDialogo();
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas eliminar el producto '" + nombreProducto + "'?\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = eliminarProductoDeDB(idProducto);

            if (eliminado) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this,
                    "Producto eliminado exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el producto.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}