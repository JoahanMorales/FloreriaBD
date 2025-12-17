package floreriabd.panel;

import Floreria.Productos;
import Floreria.Ventas;
import Floreria.DetalleVenta;
import FloreriaService.ProductosService;
import FloreriaService.VentasService;
import floreriabd.Colors;
import floreriabd.EstilosUI;
import floreriabd.FuentePersonalizada;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class VentasPanel extends JPanel {
    
    private JLabel fondo;
    private JComboBox<String> comboProductos;
    private JSpinner spinnerCantidad;
    private JButton btnAgregarCarrito;
    private JButton btnEliminarCarrito;
    private JButton btnLimpiarCarrito;
    private JButton btnProcesarVenta;
    private JButton btnRefrescarProductos;
    
    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;
    private JLabel lblTotal;
    private JComboBox<String> comboMetodoPago;
    
    private JTable tablaVentas;
    private DefaultTableModel modeloVentas;
    private JTable tablaDetalleVenta;
    private DefaultTableModel modeloDetalleVenta;
    private JButton btnRefrescarVentas;
    private List<Ventas> listaVentas;
    
    private List<Productos> listaProductos;
    private List<DetalleVenta> carrito;
    private ProductosService productosService;
    private VentasService ventasService;
    
    public VentasPanel() {
        productosService = new ProductosService();
        ventasService = new VentasService();
        listaProductos = new ArrayList<>();
        carrito = new ArrayList<>();
        listaVentas = new ArrayList<>();
        
        inicializarComponentes();
        configurarCarrito();
        configurarBotones();
        cargarProductos();
        configurarHistorialVentas();
        cargarHistorialVentas();
    }
    
    private void inicializarComponentes() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(0, 0, 1220, 570);
        
        fondo = new JLabel();
        fondo.setBounds(0, 0, 1220, 600);
        fondo.setLayout(null);
        add(fondo);
        
        JLabel lblTitulo = new JLabel("Sistema de Ventas");
        lblTitulo.setBounds(50, 20, 200, 30);
        lblTitulo.setFont(FuentePersonalizada.cargarMontserrat(18f));
        lblTitulo.setForeground(Colors.VERDE_FUERTE);
        add(lblTitulo);
        
        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(50, 70, 80, 25);
        lblProducto.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblProducto);
        
        comboProductos = new JComboBox<>();
        comboProductos.setBounds(130, 70, 300, 25);
        add(comboProductos);
        
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(450, 70, 80, 25);
        lblCantidad.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblCantidad);
        
        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        spinnerCantidad.setBounds(530, 70, 80, 25);
        add(spinnerCantidad);
        
        JLabel lblMetodo = new JLabel("Método de Pago:");
        lblMetodo.setBounds(50, 110, 120, 25);
        lblMetodo.setFont(FuentePersonalizada.cargarMontserrat(12f));
        add(lblMetodo);
        
        comboMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia"});
        comboMetodoPago.setBounds(170, 110, 150, 25);
        add(comboMetodoPago);
        
        JLabel lblTotalTexto = new JLabel("Total: $");
        lblTotalTexto.setBounds(400, 110, 60, 25);
        lblTotalTexto.setFont(FuentePersonalizada.cargarMontserrat(14f));
        add(lblTotalTexto);
        
        lblTotal = new JLabel("0.00");
        lblTotal.setBounds(460, 110, 100, 25);
        lblTotal.setFont(FuentePersonalizada.cargarMontserrat(16f));
        lblTotal.setForeground(Colors.VERDE_FUERTE);
        add(lblTotal);
    }
    
    private void configurarBotones() {
        btnAgregarCarrito = new JButton("Agregar al Carrito");
        btnAgregarCarrito.setBounds(610, 70, 150, 25);
        btnAgregarCarrito.setBackground(Colors.VERDE_CLARO);
        btnAgregarCarrito.setForeground(Color.WHITE);
        btnAgregarCarrito.setFont(FuentePersonalizada.cargarMontserrat(10f));
        btnAgregarCarrito.addActionListener(e -> agregarAlCarrito());
        add(btnAgregarCarrito);
        
        btnRefrescarProductos = new JButton("Refrescar");
        btnRefrescarProductos.setBounds(580, 150, 100, 25);
        btnRefrescarProductos.setBackground(Colors.VERDE_CLARO);
        btnRefrescarProductos.setForeground(Color.WHITE);
        btnRefrescarProductos.setFont(FuentePersonalizada.cargarMontserrat(10f));
        btnRefrescarProductos.addActionListener(e -> cargarProductos());
        add(btnRefrescarProductos);
        
        btnEliminarCarrito = new JButton("Eliminar");
        btnEliminarCarrito.setBounds(50, 150, 100, 30);
        btnEliminarCarrito.setBackground(Colors.VERDE_FUERTE);
        btnEliminarCarrito.setForeground(Color.WHITE);
        btnEliminarCarrito.setFont(FuentePersonalizada.cargarMontserrat(12f));
        btnEliminarCarrito.addActionListener(e -> eliminarDelCarrito());
        add(btnEliminarCarrito);
        
        btnLimpiarCarrito = new JButton("Limpiar Carrito");
        btnLimpiarCarrito.setBounds(160, 150, 120, 30);
        btnLimpiarCarrito.setBackground(Colors.VERDE_FUERTE);
        btnLimpiarCarrito.setForeground(Color.WHITE);
        btnLimpiarCarrito.setFont(FuentePersonalizada.cargarMontserrat(12f));
        btnLimpiarCarrito.addActionListener(e -> limpiarCarrito());
        add(btnLimpiarCarrito);
        
        btnProcesarVenta = new JButton("Procesar Venta");
        btnProcesarVenta.setBounds(580, 110, 150, 25);
        btnProcesarVenta.setBackground(Colors.VERDE_FUERTE);
        btnProcesarVenta.setForeground(Color.WHITE);
        btnProcesarVenta.setFont(FuentePersonalizada.cargarMontserrat(12f));
        btnProcesarVenta.addActionListener(e -> procesarVenta());
        add(btnProcesarVenta);
    }
    
    private void configurarHistorialVentas() {
        JLabel lblHistorialTitulo = new JLabel("Historial de Ventas");
        lblHistorialTitulo.setBounds(770, 20, 200, 30);
        lblHistorialTitulo.setFont(FuentePersonalizada.cargarMontserrat(16f));
        lblHistorialTitulo.setForeground(Colors.VERDE_FUERTE);
        add(lblHistorialTitulo);

        btnRefrescarVentas = new JButton("Refrescar");
        btnRefrescarVentas.setBounds(1070, 20, 100, 25);
        btnRefrescarVentas.setBackground(Colors.VERDE_CLARO);
        btnRefrescarVentas.setForeground(Color.WHITE);
        btnRefrescarVentas.setFont(FuentePersonalizada.cargarMontserrat(10f));
        btnRefrescarVentas.addActionListener(e -> cargarHistorialVentas());
        add(btnRefrescarVentas);

        String[] columnasVentas = {"ID", "Fecha", "Total", "Método Pago"};
        modeloVentas = new DefaultTableModel(columnasVentas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaVentas = new JTable(modeloVentas);
        tablaVentas.setFont(FuentePersonalizada.cargarMontserrat(10f));
        tablaVentas.setRowHeight(22);
        tablaVentas.setShowGrid(false);
        tablaVentas.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader headerVentas = tablaVentas.getTableHeader();
        headerVentas.setFont(FuentePersonalizada.cargarMontserrat(11f));
        headerVentas.setBackground(Colors.VERDE_CLARO);
        headerVentas.setForeground(Color.WHITE);

        tablaVentas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
                setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                return c;
            }
        });

        tablaVentas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalleVenta();
            }
        });

        JScrollPane scrollVentas = new JScrollPane(tablaVentas);
        scrollVentas.setBounds(770, 55, 400, 200);
        scrollVentas.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
        add(scrollVentas);

        JLabel lblDetalleTitulo = new JLabel("Detalle de Venta");
        lblDetalleTitulo.setBounds(770, 270, 200, 25);
        lblDetalleTitulo.setFont(FuentePersonalizada.cargarMontserrat(14f));
        lblDetalleTitulo.setForeground(Colors.VERDE_FUERTE);
        add(lblDetalleTitulo);

        // Tabla detalle de venta
        String[] columnasDetalle = {"Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        modeloDetalleVenta = new DefaultTableModel(columnasDetalle, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaDetalleVenta = new JTable(modeloDetalleVenta);
        tablaDetalleVenta.setFont(FuentePersonalizada.cargarMontserrat(10f));
        tablaDetalleVenta.setRowHeight(22);
        tablaDetalleVenta.setShowGrid(false);
        tablaDetalleVenta.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader headerDetalle = tablaDetalleVenta.getTableHeader();
        headerDetalle.setFont(FuentePersonalizada.cargarMontserrat(11f));
        headerDetalle.setBackground(Colors.VERDE_CLARO);
        headerDetalle.setForeground(Color.WHITE);

        tablaDetalleVenta.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
                setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                return c;
            }
        });

        JScrollPane scrollDetalle = new JScrollPane(tablaDetalleVenta);
        scrollDetalle.setBounds(770, 300, 400, 180);
        scrollDetalle.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
        add(scrollDetalle);
    }
    
    private void configurarCarrito() {
        String[] columnNames = {"Producto", "Precio Unit.", "Cantidad", "Subtotal"};
        
        modeloCarrito = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCarrito = new JTable(modeloCarrito);
        tablaCarrito.setFont(FuentePersonalizada.cargarMontserrat(12f));
        tablaCarrito.setRowHeight(25);
        tablaCarrito.setShowGrid(false);
        tablaCarrito.setIntercellSpacing(new Dimension(0, 0));
        
        JTableHeader header = tablaCarrito.getTableHeader();
        header.setFont(FuentePersonalizada.cargarMontserrat(14f));
        header.setBackground(Colors.VERDE_CLARO);
        header.setForeground(Color.WHITE);
        
        tablaCarrito.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        
        JScrollPane scrollPane = new JScrollPane(tablaCarrito);
        scrollPane.setBounds(50, 190, 700, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
        add(scrollPane);
    }
    
    private void cargarProductos() {
        try {
            listaProductos = productosService.obtenerTodosLosProductos();
            comboProductos.removeAllItems();
            
            for (Productos producto : listaProductos) {
                if ("Activo".equals(producto.getEstadoProd()) && producto.getStockDispo() > 0) {
                    String item = producto.getNombreProd() + " - $" + producto.getPrecioProd() + " (Stock: " + producto.getStockDispo() + ")";
                    comboProductos.addItem(item);
                }
            }
            
            if (comboProductos.getItemCount() == 0) {
                comboProductos.addItem("No hay productos disponibles");
            }
        } catch (Exception e) {
            e.printStackTrace();
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarAlCarrito() {
        int selectedIndex = comboProductos.getSelectedIndex();
        if (selectedIndex == -1 || comboProductos.getItemAt(selectedIndex).equals("No hay productos disponibles")) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, "Selecciona un producto válido", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Productos productoSeleccionado = obtenerProductoActivo(selectedIndex);
        if (productoSeleccionado == null) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, "Producto no disponible", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int cantidad = (Integer) spinnerCantidad.getValue();
        
        int cantidadEnCarrito = 0;
        for (DetalleVenta detalle : carrito) {
            if (detalle.getIdProducto() == productoSeleccionado.getIdProducto()) {
                cantidadEnCarrito = detalle.getCantidadVen();
                break;
            }
        }
        
        if (cantidad + cantidadEnCarrito > productoSeleccionado.getStockDispo()) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, 
                "Stock insuficiente. Disponible: " + productoSeleccionado.getStockDispo() + 
                ", en carrito: " + cantidadEnCarrito,
                "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DetalleVenta detalleExistente = null;
        for (DetalleVenta detalle : carrito) {
            if (detalle.getIdProducto() == productoSeleccionado.getIdProducto()) {
                detalleExistente = detalle;
                break;
            }
        }
        
        if (detalleExistente != null) {
            
            detalleExistente.setCantidadVen(detalleExistente.getCantidadVen() + cantidad);
            
        } else {
            
            DetalleVenta detalle = new DetalleVenta(cantidad, productoSeleccionado.getPrecioProd(), productoSeleccionado.getIdProducto());
            detalle.setNombreProducto(productoSeleccionado.getNombreProd());
            carrito.add(detalle);
        }
        
        actualizarTablaCarrito();
        calcularTotal();
        spinnerCantidad.setValue(1);
        
        EstilosUI.aplicarEstiloDialogo();
        JOptionPane.showMessageDialog(this, "Producto agregado al carrito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private Productos obtenerProductoActivo(int index) {
        int contador = 0;
        for (Productos producto : listaProductos) {
            if ("Activo".equals(producto.getEstadoProd()) && producto.getStockDispo() > 0) {
                if (contador == index) {
                    return producto;
                }
                contador++;
            }
        }
        return null;
    }
    
    private void eliminarDelCarrito() {
        int filaSeleccionada = tablaCarrito.getSelectedRow();
        if (filaSeleccionada == -1) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, "Selecciona un producto del carrito", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nombreProducto = carrito.get(filaSeleccionada).getNombreProducto();
        
        EstilosUI.aplicarEstiloDialogo();
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de eliminar '" + nombreProducto + "' del carrito?",
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            carrito.remove(filaSeleccionada);
            actualizarTablaCarrito();
            calcularTotal();
            
            JOptionPane.showMessageDialog(this, "Producto eliminado del carrito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void limpiarCarrito() {
        if (carrito.isEmpty()) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, "El carrito ya está vacío", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        EstilosUI.aplicarEstiloDialogo();
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de limpiar todo el carrito?",
            "Confirmar limpieza", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            carrito.clear();
            actualizarTablaCarrito();
            calcularTotal();
            
            JOptionPane.showMessageDialog(this, "Carrito limpiado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void actualizarTablaCarrito() {
        modeloCarrito.setRowCount(0);
        
        for (DetalleVenta detalle : carrito) {
            Object[] row = {
                detalle.getNombreProducto(),
                "$" + detalle.getPrecUVen(),
                detalle.getCantidadVen(),
                "$" + detalle.getSubtotalVen()
            };
            modeloCarrito.addRow(row);
        }
        
        if (tablaCarrito.getColumnCount() > 0) {
            tablaCarrito.getColumnModel().getColumn(0).setPreferredWidth(250); // Producto
            tablaCarrito.getColumnModel().getColumn(1).setPreferredWidth(100); // Precio
            tablaCarrito.getColumnModel().getColumn(2).setPreferredWidth(80);  // Cantidad
            tablaCarrito.getColumnModel().getColumn(3).setPreferredWidth(120); // Subtotal
        }
    }
    
    private void calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : carrito) {
            total = total.add(detalle.getSubtotalVen());
        }
        lblTotal.setText(String.format("%.2f", total));
    }
    
    private void procesarVenta() {
        if (carrito.isEmpty()) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, "El carrito está vacío", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!verificarStockDisponible()) {
            return;
        }
        
        EstilosUI.aplicarEstiloDialogo();
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Confirmar la venta por $" + lblTotal.getText() + "?",
            "Confirmar Venta", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {

            Ventas venta = new Ventas();
            venta.setFechaVen(LocalDate.now());
            venta.setTotalVen(new BigDecimal(lblTotal.getText()));
            venta.setMetodoPago((String) comboMetodoPago.getSelectedItem());
            venta.setEmpId(null); 
            
            boolean exitoso = ventasService.registrarVenta(venta, new ArrayList<>(carrito));
            
            if (exitoso) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Venta procesada exitosamente\n" +
                    "Total: $" + lblTotal.getText() + "\n" +
                    "Método de pago: " + comboMetodoPago.getSelectedItem(),
                    "Venta Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                generarPDFVenta(venta, new ArrayList<>(carrito));
                
                carrito.clear();
                cargarHistorialVentas();
                actualizarTablaCarrito();
                calcularTotal();
                cargarProductos(); 
                
                comboMetodoPago.setSelectedIndex(0);
                
            } else {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Error al procesar la venta. Intenta nuevamente.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, 
                "Error al procesar la venta: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean verificarStockDisponible() {

        try {
            listaProductos = productosService.obtenerTodosLosProductos();
        } catch (Exception e) {
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, 
                "Error al verificar stock: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        for (DetalleVenta detalle : carrito) {
            Productos producto = null;
            for (Productos p : listaProductos) {
                if (p.getIdProducto() == detalle.getIdProducto()) {
                    producto = p;
                    break;
                }
            }
            
            if (producto == null) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "El producto '" + detalle.getNombreProducto() + "' ya no está disponible",
                    "Producto no disponible", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (detalle.getCantidadVen() > producto.getStockDispo()) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "Stock insuficiente para '" + detalle.getNombreProducto() + "'\n" +
                    "Solicitado: " + detalle.getCantidadVen() + ", Disponible: " + producto.getStockDispo(),
                    "Stock Insuficiente", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (!"Activo".equals(producto.getEstadoProd())) {
                EstilosUI.aplicarEstiloDialogo();
                JOptionPane.showMessageDialog(this, 
                    "El producto '" + detalle.getNombreProducto() + "' ya no está activo",
                    "Producto inactivo", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return true;
    }
    
    public void refrescarPanel() {
        cargarProductos();
        if (!carrito.isEmpty()) {
            verificarStockDisponible();
            actualizarTablaCarrito();
            calcularTotal();
        }
    }
    
    public BigDecimal getTotalActual() {
        return new BigDecimal(lblTotal.getText());
    }
    
    public boolean tieneVentaEnProceso() {
        return !carrito.isEmpty();
    }
    
    private void cargarHistorialVentas() {
        try {
            listaVentas = ventasService.obtenerTodasLasVentas();
            modeloVentas.setRowCount(0);

            for (Ventas venta : listaVentas) {
                Object[] row = {
                    venta.getIdVenta(),
                    venta.getFechaVen().toString(),
                    "$" + String.format("%.2f", venta.getTotalVen()),
                    venta.getMetodoPago()
                };
                modeloVentas.addRow(row);
            }

            if (tablaVentas.getColumnCount() > 0) {
                tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
                tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(100); // Fecha
                tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(80);  // Total
                tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(100); // Método
            }

            modeloDetalleVenta.setRowCount(0);

        } catch (Exception e) {
            e.printStackTrace();
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, "Error al cargar historial de ventas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarPDFVenta(Ventas venta, List<DetalleVenta> detallesVenta) {
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar recibo de venta");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String nombreArchivo = "Recibo_Venta_" + venta.getIdVenta() + "_" + sdf.format(new Date()) + ".pdf";
            fileChooser.setSelectedFile(new java.io.File(nombreArchivo));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                if (!rutaArchivo.toLowerCase().endsWith(".pdf")) {
                    rutaArchivo += ".pdf";
                }

                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));

                document.open();

                Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
                Font subtituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
                Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
                Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);

                Paragraph titulo = new Paragraph("RECIBO DE VENTA", tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(20);
                document.add(titulo);

                Paragraph infoEmpresa = new Paragraph("Raices Politécncias", subtituloFont);
                infoEmpresa.setAlignment(Element.ALIGN_CENTER);
                document.add(infoEmpresa);

                Paragraph direccion = new Paragraph("Upiicsa", normalFont);
                direccion.setAlignment(Element.ALIGN_CENTER);
                document.add(direccion);

                Paragraph telefono = new Paragraph("Miiiiel", normalFont);
                telefono.setAlignment(Element.ALIGN_CENTER);
                telefono.setSpacingAfter(20);
                document.add(telefono);

                LineSeparator line = new LineSeparator();
                document.add(new Chunk(line));
                document.add(Chunk.NEWLINE);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                Paragraph infoVenta = new Paragraph();
                infoVenta.add(new Chunk("ID Venta: ", boldFont));
                infoVenta.add(new Chunk(String.valueOf(venta.getIdVenta()), normalFont));
                infoVenta.add(Chunk.NEWLINE);
                infoVenta.add(new Chunk("Fecha: ", boldFont));
                infoVenta.add(new Chunk(dateFormat.format(new Date()), normalFont));
                infoVenta.add(Chunk.NEWLINE);
                infoVenta.add(new Chunk("Método de Pago: ", boldFont));
                infoVenta.add(new Chunk(venta.getMetodoPago(), normalFont));
                infoVenta.setSpacingAfter(15);
                document.add(infoVenta);

                PdfPTable tabla = new PdfPTable(4);
                tabla.setWidthPercentage(100);
                tabla.setSpacingBefore(10);

                float[] columnWidths = {40f, 15f, 20f, 25f};
                tabla.setWidths(columnWidths);

                PdfPCell headerProducto = new PdfPCell(new Phrase("Producto", boldFont));
                headerProducto.setBackgroundColor(new BaseColor(200, 200, 200));
                headerProducto.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerProducto.setPadding(8);
                tabla.addCell(headerProducto);

                PdfPCell headerCantidad = new PdfPCell(new Phrase("Cant.", boldFont));
                headerCantidad.setBackgroundColor(new BaseColor(200, 200, 200));
                headerCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCantidad.setPadding(8);
                tabla.addCell(headerCantidad);

                PdfPCell headerPrecio = new PdfPCell(new Phrase("Precio Unit.", boldFont));
                headerPrecio.setBackgroundColor(new BaseColor(200, 200, 200));
                headerPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerPrecio.setPadding(8);
                tabla.addCell(headerPrecio);

                PdfPCell headerSubtotal = new PdfPCell(new Phrase("Subtotal", boldFont));
                headerSubtotal.setBackgroundColor(new BaseColor(200, 200, 200));
                headerSubtotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerSubtotal.setPadding(8);
                tabla.addCell(headerSubtotal);

                for (DetalleVenta detalle : detallesVenta) {
                    
                    PdfPCell cellProducto = new PdfPCell(new Phrase(detalle.getNombreProducto(), normalFont));
                    cellProducto.setPadding(5);
                    tabla.addCell(cellProducto);
                    
                    PdfPCell cellCantidad = new PdfPCell(new Phrase(String.valueOf(detalle.getCantidadVen()), normalFont));
                    cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellCantidad.setPadding(5);
                    tabla.addCell(cellCantidad);
                    
                    PdfPCell cellPrecio = new PdfPCell(new Phrase("$" + String.format("%.2f", detalle.getPrecUVen()), normalFont));
                    cellPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cellPrecio.setPadding(5);
                    tabla.addCell(cellPrecio);

                    PdfPCell cellSubtotal = new PdfPCell(new Phrase("$" + String.format("%.2f", detalle.getSubtotalVen()), normalFont));
                    cellSubtotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cellSubtotal.setPadding(5);
                    tabla.addCell(cellSubtotal);
                }

                document.add(tabla);

                Paragraph total = new Paragraph();
                total.setAlignment(Element.ALIGN_RIGHT);
                total.setSpacingBefore(15);
                total.add(new Chunk("TOTAL: $" + String.format("%.2f", venta.getTotalVen()), 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK)));
                document.add(total);

                Paragraph agradecimiento = new Paragraph();
                agradecimiento.setAlignment(Element.ALIGN_CENTER);
                agradecimiento.setSpacingBefore(30);
                agradecimiento.add(new Chunk("¡Gracias por su compra!", subtituloFont));
                agradecimiento.add(Chunk.NEWLINE);
                agradecimiento.add(new Chunk("Esperamos verle pronto", normalFont));
                document.add(agradecimiento);

                document.close();

                EstilosUI.aplicarEstiloDialogo();
                int respuesta = JOptionPane.showConfirmDialog(this, 
                    "PDF generado exitosamente en:\n" + rutaArchivo + "\n\n¿Desea abrir el archivo?",
                    "PDF Generado", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        java.awt.Desktop.getDesktop().open(new java.io.File(rutaArchivo));
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, 
                            "No se pudo abrir el archivo automáticamente.\nUbicación: " + rutaArchivo,
                            "Información", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, 
                "Error al generar el PDF: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void mostrarDetalleVenta() {
        int filaSeleccionada = tablaVentas.getSelectedRow();
        if (filaSeleccionada == -1) {
            modeloDetalleVenta.setRowCount(0);
            return;
        }

        try {
            Ventas ventaSeleccionada = listaVentas.get(filaSeleccionada);
            List<DetalleVenta> detalles = ventasService.obtenerDetallesVenta(ventaSeleccionada.getIdVenta());

            modeloDetalleVenta.setRowCount(0);

            for (DetalleVenta detalle : detalles) {
                String nombreProducto = "Producto ID: " + detalle.getIdProducto();
                try {
                    for (Productos producto : listaProductos) {
                        if (producto.getIdProducto() == detalle.getIdProducto()) {
                            nombreProducto = producto.getNombreProd();
                            break;
                        }
                    }
                } catch (Exception e) {
                }

                Object[] row = {
                    nombreProducto,
                    detalle.getCantidadVen(),
                    "$" + String.format("%.2f", detalle.getPrecUVen()),
                    "$" + String.format("%.2f", detalle.getSubtotalVen())
                };
                modeloDetalleVenta.addRow(row);
            }

            if (tablaDetalleVenta.getColumnCount() > 0) {
                tablaDetalleVenta.getColumnModel().getColumn(0).setPreferredWidth(150); // Producto
                tablaDetalleVenta.getColumnModel().getColumn(1).setPreferredWidth(70);  // Cantidad
                tablaDetalleVenta.getColumnModel().getColumn(2).setPreferredWidth(90);  // Precio Unit
                tablaDetalleVenta.getColumnModel().getColumn(3).setPreferredWidth(90);  // Subtotal
            }

        } catch (Exception e) {
            e.printStackTrace();
            EstilosUI.aplicarEstiloDialogo();
            JOptionPane.showMessageDialog(this, "Error al cargar detalle de venta: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}