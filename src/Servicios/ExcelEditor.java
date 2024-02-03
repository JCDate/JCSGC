package Servicios;

import Modelos.AceptacionPc1;
import Modelos.AceptacionPc2;
import Modelos.AceptacionPc3;
import Modelos.AnchoLargoM;
import Modelos.DatosFila;
import Modelos.DatosIRM;
import Modelos.InspeccionReciboM;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author JC
 */
public class ExcelEditor {

    String nuevaRutaArchivo = ""; // Variable para guardar la información de la ruta del archivo de hoja de Instrucción
    String nuevaRutaArchivoRD = ""; // Variable para guardar la información de la ruta del archivo de retención dimensional
    ExcelFormato formato = new ExcelFormato(); // Clase para aplicar formatos de Excel
    AceptacionProductoServicio aps = new AceptacionProductoServicio(); // Instancia de la clase de aceptaciónProducto
    String numeroStr; // Se obtiene el nombre del componente para anexarlo en el nombre del archivo de exce

    // Filas a partir de donde se van a imprimir los datos de los procesos de Retención Dimensional
    int filaBaseNoOp1 = 9;
    int filaBaseNoOp3 = 47;
    int filaBaseNoOp4 = 85;
    int filaBaseNoOp5 = 123;
    
    /*
            getProceso(mapVariablesNoOp12, columnaBase, listVariables1, filaBaseNoOp1, sheet2, 5, workbook, 5);
        getProceso(mapVariablesNoOp3, columnaBase, listVariables3, filaBaseNoOp3, sheet2, 43, workbook, 43);
        getProceso(mapVariablesNoOp4, columnaBase, listVariables4, filaBaseNoOp4, sheet2, 81, workbook, 81);
        getProceso(mapVariablesNoOp5, columnaBase, listVariables5, filaBaseNoOp5, sheet2, 119, workbook, 119);
    
    */
    int fila1 = 5;
    int fila3 = 43;
    int fila4 = 81;
    int fila5 = 119;
    
    
    int columnaBase = 8; // A partir de esta fila se imprimen las variables y sus respectivos valores en el documento de Retención Dimensional

    private static final int[] FILAS_INFO = {2, 40, 78, 116}; // 
    private static final int[] FILAS_PROCESOS = {4, 42, 80, 118}; // Celdas donde se imprimen las "x" de los procesos 
    private static final int[] COLUMNAS_COMPONENTE = {13, 20, 26}; // Columnas donde se imprime la información del componente

    public String generarHojaInstruccion(Connection conexion, DatosIRM dirm, InspeccionReciboM irm, JTable tblAL, List<JTable> listTablas, List listaAL) throws IOException, SQLException, ClassNotFoundException {
        String filePath = "HojaInstruccion.xlsx"; // Se obtiene el formato editable de la Hoja de Instrucción

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            procesarPaginaUnoHJ(workbook, irm, dirm, listaAL);
            procesarPaginaDosHJ(workbook, conexion, dirm, listTablas);

            // Crear un nuevo archivo de Excel para guardar los cambios
            nuevaRutaArchivo = "HI-" + numeroStr + "-" + formato.eliminarSeparadores(dirm.getFechaInspeccion()) + ".xlsx ";  // Ruta del nuevo archivo de Excel
            FileOutputStream fos = new FileOutputStream(nuevaRutaArchivo);
            workbook.write(fos);
        } catch (IOException e) {
        }
        return nuevaRutaArchivo;
    }

    private void procesarPaginaUnoHJ(XSSFWorkbook workbook, InspeccionReciboM irm, DatosIRM dirm, List listaAL) {
        Sheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del libro

        String[] partes = irm.getNoHoja().split("/"); // Se divide el valor de NoHoja para solo obtener el número
        numeroStr = partes[1];
        int numero = Integer.parseInt(numeroStr);
        if (numero < 10) { // Eliminar ceros a la izquierda
            numeroStr = String.valueOf(numero);
        }
        workbook.setSheetName(workbook.getSheetIndex(sheet), numeroStr); // Cambiar el nombre de la Hoja con el número previamente obtenido

        // Se imprime la información referente
        setValorCeldasHojaInstruccion(sheet, 5, 2, numeroStr);  // No Hoja
        setValorCeldasHojaInstruccion(sheet, 5, 6, formato.formatoFecha(dirm.getFechaInspeccion()));  // Fecha Inspección
        setValorCeldasHojaInstruccion(sheet, 9, 1, dirm.getDescripcionMP());  // Descripcion de Materia Prima Recibida
        setValorCeldasHojaInstruccion(sheet, 9, 4, irm.getProveedor());  // Proveedor
        setValorCeldasHojaInstruccion(sheet, 9, 6, irm.getNoFactura());  // No. Factura
        setValorCeldasHojaInstruccion(sheet, 9, 8, irm.getFechaFactura());  // Fecha Factura
        setValorCeldasHojaInstruccion(sheet, 13, 1, dirm.getCalibreLamina());  // Calibre
        setValorCeldasHojaInstruccion(sheet, 13, 3, irm.getNoPedido());  // No. Pedido
        setValorCeldasHojaInstruccion(sheet, 13, 5, irm.getNoRollo());  // No. Rollo
        setValorCeldasHojaInstruccion(sheet, 13, 7, irm.getPzKg());  // No. PzKg

        int numeroFila = 20; // Tabla Ancho/Largo

        for (int i = 0; i < listaAL.size(); i++) { // Se imprimen los valores de ancho y largo del componente
            AnchoLargoM medida = (AnchoLargoM) listaAL.get(i);
            Row row = sheet.getRow(numeroFila);
            if (row == null) {
                row = sheet.createRow(numeroFila);
            }

            Cell cellAncho = row.createCell(1); // Celda para el ancho
            cellAncho.setCellValue(medida.getAncho());

            Cell cellLargo = row.createCell(2); // Celda para el largo
            cellLargo.setCellValue(medida.getLargo());

            numeroFila++;

            //Aplicación del formato
            cellAncho.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            cellLargo.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
        }

        // Aceptación/Rechazo
        int numCellAR = (dirm.getAceptacion() == 1) ? 2 : 7;
        Row rowAR = sheet.getRow(39); // Obtén la fila de donde se encuentra la opción de aceptación o rechazo
        Cell celdaAR = rowAR.getCell(numCellAR); // Obtén la celda en la fila

        Row row = sheet.getRow(39);
        Cell celdaAR2 = (numCellAR == 2) ? row.getCell(7) : row.getCell(2); // Obtiene la fila contraria al aceptación o rechazo y se invierte el valor
        celdaAR2.setCellValue(""); // Modifica el valor de la celda  
        celdaAR.setCellValue("√"); // Modifica el valor de la celda

        XSSFFont fuente = formato.crearFuente(workbook, "Calibri", (short) 10, false); // Se crea un nuevo estilo de fuente
        CellStyle estilo = formato.bordeGrueso(workbook); // Crear un estilo de celda y establecer el borde grueso
        estilo.setFont(fuente); // Se aplica el formato de la fuente

        celdaAR2.setCellStyle(estilo); // Aplicar el estilo a la celda

        setValorCeldasHojaInstruccion(sheet, 34, 1, dirm.getObservacionesRD());  // Observaciones a los resultados dimensionales
        setValorCeldasHojaInstruccion(sheet, 42, 1, dirm.getObsMP());  // Observaciones a la disposición de la Materia Prima
        setValorCeldasHojaInstruccion(sheet, 60, 1, dirm.getInspector());  // Inspector
    }

    private void procesarPaginaDosHJ(XSSFWorkbook workbook, Connection conexion, DatosIRM dirm, List<JTable> listTablas) throws SQLException {
        Sheet sheet2 = workbook.getSheetAt(1); // Obtén la segunda hoja del libro
        workbook.setSheetName(workbook.getSheetIndex(sheet2), formato.eliminarSeparadores(dirm.getFechaInspeccion())); // Cambiar el nombre de la Hoja

        List<DatosFila> datosFilas = new ArrayList<>(); // Obtener la lista de tablas

        for (JTable table : listTablas) { // Se obtiene la información de las especificaciones de los componentes
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) { // Iterar sobre las filas de la JTable
                Object especificacion = model.getValueAt(0, 0);
                Object calibres = model.getValueAt(0, 2);
                Object propiedad = model.getValueAt(i, 3);
                Object cumplePropiedad = model.getValueAt(i, 4);
                Object composicion = model.getValueAt(i, 5);
                Object cumpleComposicion = model.getValueAt(i, 6);
                int fila = aps.obtenerEspecificacion(conexion, especificacion.toString());
                DatosFila datosFila = new DatosFila(especificacion, calibres, propiedad, cumplePropiedad, composicion, cumpleComposicion, fila);
                datosFilas.add(datosFila);
            }
        }

        // Variables para el control de las filas
        int numeroFilaEspecificacion = 6; // Valor inicial de la fila para la primera especificación
        String especificacionAnterior = "";

        XSSFFont fuente2 = formato.crearFuente(workbook, "Calibri", (short) 10, false);
        CellStyle estilo2 = formato.bordeGrueso(workbook); // Crear un estilo de celda y establecer el borde grueso
        estilo2.setFont(fuente2);

        // Establecer el formato de fuente para el contenido
        CellStyle estilo3 = workbook.createCellStyle();
        XSSFFont fuente3 = formato.crearFuente(workbook, "Arial", (short) 6, false);
        estilo3.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        estilo3.setFont(fuente3);

        boolean primeraEspecificacion = true; // Variable para rastrear la primera aparición de la especificación

        for (DatosFila datosFila : datosFilas) { // Recorrer los elementos
            String especificacionActual = datosFila.getEspecificacion().toString(); // Obtener la especificación actual
            if (!especificacionActual.equals(especificacionAnterior)) { // Verificar si es una nueva especificación
                numeroFilaEspecificacion = datosFila.getElemento(especificacionActual); // Cambiar a la siguiente fila específica para la nueva especificación
                primeraEspecificacion = true; // Reiniciar la variable para la primera especificación
            }

            Row row2 = sheet2.getRow(numeroFilaEspecificacion); // Crear la fila en el archivo de Excel
            if (row2 == null) {
                row2 = sheet2.createRow(numeroFilaEspecificacion);
            }

            CellStyle estiloCheckbox = workbook.createCellStyle(); // Crear estilo de celda con checkbox
            estiloCheckbox.setLocked(true);

            if (primeraEspecificacion) { // Imprimir el valor de calibre solo en la primera aparición de la especificación
                Cell chkCalibre = row2.createCell(4);
                Cell cellCalibre = row2.createCell(5);
                cellCalibre.setCellValue(datosFila.getCalibre().toString());
                cellCalibre.setCellStyle(estilo3);
                chkCalibre.setCellValue("R");
                chkCalibre.setCellStyle(formato.estiloTablaAnchoLargoHI(workbook));
                primeraEspecificacion = false; // Marcar que ya se imprimió el calibre
            }

            // Escribir los datos en las celdas correspondientes referentes a las propiedades de los componentes
            Cell cellCumplePropiedad = row2.createCell(10);
            if (datosFila.getPropiedad() == null || datosFila.getPropiedad().equals("")) {
                cellCumplePropiedad.setCellValue("");
            } else {
                String cmPm = (datosFila.getCmPm().equals(true)) ? "X" : "√";
                cellCumplePropiedad.setCellValue(cmPm);
            }
            cellCumplePropiedad.setCellStyle(estilo2);

            // Escribir los datos en las celdas correspondientes referentes a la composición química de los componentes
            Cell cellCumpleComposicion = row2.createCell(13);
            if (datosFila.getComposicion() == null || datosFila.getComposicion().equals("")) {
                cellCumpleComposicion.setCellValue("");
            } else {
                String cmCq = (datosFila.getCmCq().toString().equals(true)) ? "X" : "√";
                cellCumpleComposicion.setCellValue(cmCq);
            }

            cellCumpleComposicion.setCellStyle(estilo2);
            numeroFilaEspecificacion++; // Incrementar el número de fila correspondiente a la especificación actual
            especificacionAnterior = especificacionActual;
        }
    }

    public String generarExcelRD(Connection conexion, List<AceptacionPc1> ap1m, AceptacionProductoServicio aps, JComboBox cbxComponente, List<AceptacionPc3> ap3m, AceptacionPc2 apc2) throws IOException, SQLException, ClassNotFoundException {
        String filePath = "RETENCION-DIMENSIONAL.xlsx"; // Se obtiene el formato editable de la Hoja de Instrucción

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            ap1m = aps.recuperarAP1(conexion, cbxComponente.getSelectedItem().toString()); // Se recupera la información sobre los componentes

            if (ap1m == null || ap1m.isEmpty()) { // Si es null y vacío...
                JOptionPane.showMessageDialog(null, "No se encontró información sobre los componentes");
            } else {
                procesarPaginaUnoRD(workbook, ap1m);
                procesarPaginaDosRD(conexion, workbook, ap1m, ap3m, aps, apc2);
            }

            // Crear un nuevo archivo de Excel para guardar los cambios
            String newFilePath = "-" + ap1m.get(0).getComponente() + ".xlsx";
            try (FileOutputStream fos = new FileOutputStream(newFilePath)) {
                workbook.write(fos);
                nuevaRutaArchivoRD = newFilePath;
            }
        } catch (IOException e) {
            aps.manejarExcepcion("Error al procesar el archivo Excel: ", e); // Se muestra el mensaje de la excepción al usuario
        }
        return nuevaRutaArchivoRD;
    }

    private void procesarPaginaUnoRD(XSSFWorkbook workbook, List<AceptacionPc1> ap1m) {
        int fila = 6;
        Set<String> valoresUnicos = new HashSet<>(); // Mapa para guardar datos únicos 

        for (int i = 0; i < ap1m.size(); i++) {
            Sheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del libro
            AceptacionPc1 aceptacionPc1 = ap1m.get(i);

            // Verifica si los valores ya han sido impresos
            String claveUnica = aceptacionPc1.getFecha() + aceptacionPc1.getNoRollo() + aceptacionPc1.getInspVisual() + aceptacionPc1.getObservacion();

            if (valoresUnicos.contains(claveUnica)) { // Si los valores ya han sido impresos, pasa al siguiente registro
                continue;
            }

            // Se agrega la información en la hoja 1
            agregarFilaInformacion(sheet, fila, 3, aceptacionPc1.getFecha()); // Fecha
            agregarFilaInformacion(sheet, fila, 8, aceptacionPc1.getNoRollo()); // NoRollo
            agregarFilaInformacion(sheet, fila, 11, aceptacionPc1.getInspVisual()); // Inspección Visual
            agregarFilaInformacion(sheet, fila, 16, aceptacionPc1.getObservacion()); // Observación

            valoresUnicos.add(claveUnica); // Agrega la clave única al conjunto para evitar imprimir los mismos valores

            if (fila == 34) { // Si la fila es igual a 35...
                fila = 41; // Que se pase a la siguiente lista, que comienza en la fila 41
            }
            fila++;
        }
    }

    private void procesarPaginaDosRD(Connection conexion, XSSFWorkbook workbook, List<AceptacionPc1> ap1m, List<AceptacionPc3> ap3m, AceptacionProductoServicio aps, AceptacionPc2 apc2) {
        XSSFSheet sheet2 = workbook.getSheetAt(1); // Obtén la segunda hoja del libro

        procesarComponentes(sheet2, ap1m); // Se muestra la información referente al componente en las celdas de excel
        procesarProcesos(sheet2, ap1m); // Se muestran el total de Procesos del componente en el documento de excel

        Comparator<String> dateComparator = new Comparator<String>() { // Se llama esta clase para comparar las fechas de los registros y ordenarlas
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public int compare(String fecha1, String fecha2) {
                try {
                    Date date1 = sdf.parse(fecha1);
                    Date date2 = sdf.parse(fecha2);
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    return 0;
                }
            }
        };

        // Se crean los mapas para almacenar la información según su número de Operación
        TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp12 = crearTreeMap(dateComparator);
        TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp3 = crearTreeMap(dateComparator);
        TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp4 = crearTreeMap(dateComparator);
        TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp5 = crearTreeMap(dateComparator);

        formato.estiloCeldasRD(workbook, 7, 90, true); // Se aplica el estilo a las celdas en el documento de excel

        // Listas para almacenar las variables en orden
        List<String> listVariables1 = new ArrayList<>();
        List<String> listVariables3 = new ArrayList<>();
        List<String> listVariables4 = new ArrayList<>();
        List<String> listVariables5 = new ArrayList<>();

        TreeMap<String, TreeMap<String, Map<String, String>>> selectedMap; // Variable para seleccionar el mapa y guardar los datos en el mapa elegido
        for (AceptacionPc3 dataVariable : ap3m) {
            String variable = dataVariable.getVariable() + "\n" + dataVariable.getEspecificacion(); // Se obtiene la variable y su especificación
            String valor = dataVariable.getValor(); // Se Obtiene el valor

            switch (dataVariable.getNoOp()) { // Según el número de operación...
                case "1":
                case "2":
                    selectedMap = mapVariablesNoOp12; // Se elije un mapa
                    if (!listVariables1.contains(variable)) { // Si esa variable no estaba ya en el mapa...
                        listVariables1.add(variable);
                    }
                    break;
                case "3":
                    selectedMap = mapVariablesNoOp3;
                    if (!listVariables3.contains(variable)) {
                        listVariables3.add(variable);
                    }
                    break;
                case "4":
                    selectedMap = mapVariablesNoOp4;
                    if (!listVariables4.contains(variable)) {
                        listVariables4.add(variable);
                    }
                    break;
                case "5":
                    selectedMap = mapVariablesNoOp5;
                    if (!listVariables5.contains(variable)) {
                        listVariables5.add(variable);
                    }
                    break;
                default:
                    selectedMap = null; // En caso de valores de noOp desconocidos
                    break;
            }

            try {
                // Busca el objeto correspondiente en ap2m (usando un identificador único como ejemplo)
                String id = dataVariable.getFecha(); // Reemplaza esto con el campo adecuado en ap3m que relaciona con ap2m
                String comp = dataVariable.getComponente(); // Reemplaza esto con el campo adecuado en ap3m que relaciona con ap2m
                List<AceptacionPc2> objetoAp2m = aps.buscarObjetoEnAp2mPorFecha(conexion, id, comp, apc2); // Implementa una función que busque en ap2m por el id    } catch (SQLException ex) {
                if (selectedMap != null) {
                    selectedMap.computeIfAbsent(dataVariable.getFecha(), k -> new TreeMap<>())
                            .computeIfAbsent(dataVariable.getNoOp(), k -> new LinkedHashMap<>())
                            .put(variable, valor);
                    // Agrega campos de ap2m
                    int numO = 0;
                    for (AceptacionPc2 dataVariable3 : objetoAp2m) {
                        // Agrega el tamaño y el número de orden al mapa
                        Map<String, String> variablesMap = selectedMap.get(dataVariable.getFecha()).get(dataVariable.getNoOp());
                        variablesMap.put("noOrden" + numO, dataVariable3.getNoOrden());
                        variablesMap.put("tamLote" + numO, dataVariable3.getTamLote());
                        variablesMap.put("tamMta" + numO, dataVariable3.getTamMta());
                        variablesMap.put("insp" + numO, dataVariable3.getInspector());
                        variablesMap.put("turno" + numO, dataVariable3.getTurno());
                        variablesMap.put("disp" + numO, dataVariable3.getDisp());
                    }
                }
            } catch (SQLException ex) {
                aps.manejarExcepcion("Hubo un error al obtener la información de la base de datos: ", ex);
            }
        }

        // Se Imprimen los valores correspondientes en las filas y columnas
        getProceso(mapVariablesNoOp12, columnaBase, listVariables1, filaBaseNoOp1, sheet2, fila1, workbook, 5);
        getProceso(mapVariablesNoOp3, columnaBase, listVariables3, filaBaseNoOp3, sheet2, fila3, workbook, 43);
        getProceso(mapVariablesNoOp4, columnaBase, listVariables4, filaBaseNoOp4, sheet2, fila4, workbook, 81);
        getProceso(mapVariablesNoOp5, columnaBase, listVariables5, filaBaseNoOp5, sheet2, fila5, workbook, 119);

    }

    private void getProceso(TreeMap<String, TreeMap<String, Map<String, String>>> map, int columnaBase, List<String> listVariables, int fila, Sheet sheet2, int rows, XSSFWorkbook workbook, int filaD) {
        int tamanoEntrada2 = calcularTamanoEntrada(map); // Calcula el tamaño del mapa
        int filasNecesarias = Math.max(0, tamanoEntrada2 - 28); // Calcula si se deben agregar más filas al documento

        ajustarFilasNecesarias(sheet2, fila, filasNecesarias, columnaBase, listVariables, filaD, tamanoEntrada2); // Ajusta las filas necesarias en caso de que filasNecesarias sea mayor a 0

        for (Map.Entry<String, TreeMap<String, Map<String, String>>> entrada1 : map.entrySet()) {
            for (Map.Entry<String, Map<String, String>> entrada2 : entrada1.getValue().entrySet()) {
                String noOp = entrada2.getKey(); // Convertir noOp a entero
                Row row = sheet2.getRow(fila);
                fila++; // Incrementar la fila para el próximo valor con noOp igual a 1
                if (row != null) {
                    Cell cellNoOp = validarCeldas(row, 0, noOp); // Número de Operación
                    Cell cellFecha = validarCeldas(row, 1, entrada1.getKey()); // Fecha

                    Row variableRow = sheet2.getRow(rows);
                    if (variableRow == null) {
                        variableRow = sheet2.createRow(rows);
                    }

                    int variableColumna = 9; // Se comienzan a imprimir desde esta columna la información de las variables
                    int celdaBarra = 8; // Se comienzan a imprimir las diagonales que dividen las variables
                    int celdaVar = 9; // Se comienzan a imprimir los valores de las variables

                    for (String variable : listVariables) {
                        Cell variableCelda = variableRow.getCell(variableColumna);
                        if (variableCelda == null) { // Si la celda es nula...
                            variableCelda = variableRow.createCell(variableColumna); // Si la celda es nula, crea una nueva celda en su lugar

                            sheet2.addMergedRegion(new CellRangeAddress(filaD, filaD + 3, celdaBarra, celdaBarra)); // Se combina las celdas donde se muestra el diagonal
                            sheet2.addMergedRegion(new CellRangeAddress(filaD, filaD + 3, celdaVar, celdaVar)); // Se combina las celdas para imprimir las variables y su especificación
                            sheet2.setColumnWidth(celdaBarra, 570); // Establece el ancho de la columna 0
                            sheet2.setColumnWidth(celdaVar, 1450); // Establece el ancho de la columna 0

                            // Crear filas y celdas
                            Row celdaOrigen = sheet2.getRow(filaD);
                            Cell sourceCell = celdaOrigen.getCell(10);

                            Row targetRow = sheet2.getRow(filaD);
                            Cell targetCell = targetRow.createCell(celdaBarra);

                            // Crear filas y celdas
                            Row sourceRow2 = sheet2.getRow(filaD);
                            Cell sourceCell2 = sourceRow2.getCell(9);

                            Row targetRow2 = sheet2.getRow(filaD);
                            Cell targetCell2 = targetRow2.createCell(celdaVar);

                            // Crear un estilo de celda
                            CellStyle cellStyle4 = workbook.createCellStyle();
                            CellStyle cellStyle5 = workbook.createCellStyle();

                            cellStyle4.cloneStyleFrom(sourceCell.getCellStyle()); // Copia el estilo de la celda de origen
                            cellStyle5.cloneStyleFrom(sourceCell2.getCellStyle()); // Copia el estilo de la celda de origen
                            sheet2.setColumnWidth(celdaBarra, 600);

                            // Aplicar el estilo a la celda de destino
                            targetCell.setCellStyle(cellStyle4);
                            targetCell2.setCellStyle(cellStyle5);

                        }

                        variableCelda.setCellValue(variable);
                        celdaBarra += 2;
                        celdaVar += 2;
                        variableColumna += 2;

                    }

                    Map<String, String> variablesMap = entrada2.getValue();
                    int columna = columnaBase; // Inicializar la columna en función de columnaBase
                    for (String variable : listVariables) {
                        Cell cellVariable = row.getCell(columna);
                        Cell cellVariable2 = row.getCell(columna + 1);
                        if (cellVariable == null || columna > 42 || cellVariable2 == null || columna > 42) { // Si la celda es nula, crea una nueva celda en su lugar
                            cellVariable = row.createCell(columna);
                            cellVariable2 = row.createCell(columna + 1);
                        }

                        if (variablesMap.containsKey(variable)) { // Si hay un valor para esa variable, se imprime, sino, se muestra "N/A"
                            cellVariable.setCellValue(variablesMap.get(variable));
                        } else {
                            cellVariable.setCellValue("N/A");
                        }

                        int columnaBase2 = 30; // Definir la columna base a partir de la cual se combinarán las celdas en pares

                        // Crear el estilo de celda fuera del bucle
                        CellStyle cellStyle = workbook.createCellStyle();
                        Row sourceRow = sheet2.getRow(9);
                        Cell sourceCell = sourceRow.getCell(columnaBase);

                        cellStyle.cloneStyleFrom(sourceCell.getCellStyle()); // Clonar el estilo de celda de origen

                        for (String variable3 : listVariables) {
                            int columna2 = columnaBase2; // Ajustar la columna en función de la columna base

                            if (cellVariable == null || cellVariable2 == null) { // Si la celda es nula, crea una nueva celda en su lugar
                                cellVariable = row.createCell(columna2);
                                cellVariable2 = row.createCell(columna2 + 1);

                                // Configurar el estilo de celda clonado para la nueva celda
                                cellVariable.setCellStyle(cellStyle);
                                cellVariable2.setCellStyle(cellStyle);
                            }

                            // Configurar el valor de la celda
                            if (variablesMap.containsKey(variable3)) {
                                cellVariable.setCellValue(variablesMap.get(variable3));
                            } else {
                                cellVariable.setCellValue("N/A");
                            }

                            // Incrementar la columna para la siguiente iteración
                            columnaBase2 += 2;

                            // Combinar las celdas en pares
                            CellRangeAddress region = new CellRangeAddress(
                                    row.getRowNum(), // Fila inicial
                                    row.getRowNum(), // Fila final (misma fila)
                                    columna2, // Columna inicial
                                    columna2 + 1 // Columna final (celda siguiente)
                            );

                            // Verificar si la región ya está combinada
                            boolean combinada = false;
                            for (int i = 0; i < sheet2.getNumMergedRegions(); i++) {
                                CellRangeAddress mergedRegion = sheet2.getMergedRegion(i);
                                if (mergedRegion.getFirstRow() == region.getFirstRow()
                                        && mergedRegion.getLastRow() == region.getLastRow()
                                        && mergedRegion.getFirstColumn() == region.getFirstColumn()
                                        && mergedRegion.getLastColumn() == region.getLastColumn()) {
                                    combinada = true;
                                    break;
                                }
                            }

                            if (!combinada) { // Agregar la región combinada solo si no está combinada previamente
                                sheet2.addMergedRegion(region);
                            }
                        }

                        // Crear filas y celdas copiando el estilo de las celdas ya previamente definidos
                        Row sourceRow3 = sheet2.getRow(9);
                        Cell sourceCell3 = sourceRow.getCell(8);

                        Row sourceRow2 = sheet2.getRow(9);
                        Cell sourceCell2 = sourceRow2.getCell(9);

                        // Crear un estilo de celda
                        CellStyle cellStyle4 = workbook.createCellStyle();
                        CellStyle cellStyle5 = workbook.createCellStyle();

                        cellStyle4.cloneStyleFrom(sourceCell.getCellStyle()); // Copia el estilo de la celda de origen
                        cellStyle5.cloneStyleFrom(sourceCell2.getCellStyle()); // Copia el estilo de la celda de origen

                        cellVariable.setCellStyle(cellStyle4);
                        cellVariable2.setCellStyle(cellStyle5);

                        columna += 2;
                    }

                    // Concatenar valores de 'noOrden'
                    Set<String> valoresUnicosNoOrden = new HashSet<>();
                    Set<String> valoresUnicosTamLote = new HashSet<>();
                    Set<String> valoresUnicosTamMta = new HashSet<>();
                    Set<String> valoresUnicosInsp = new HashSet<>();
                    Set<String> valoresUnicosTurno = new HashSet<>();
                    Set<String> valoresUnicosDisp = new HashSet<>();

                    // Iterar sobre los valores y agregarlos a los conjuntos correspondientes
                    variablesMap.entrySet().forEach((variableEntry) -> {
                        String variableKey = variableEntry.getKey();
                        String valor = variableEntry.getValue();

                        if (variableKey.startsWith("noOrden")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosNoOrden.add(valor);
                            }
                        } else if (variableKey.startsWith("tamLote")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTamLote.add(valor);
                            }
                        } else if (variableKey.startsWith("tamMta")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTamMta.add(valor);
                            }
                        } else if (variableKey.startsWith("insp")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosInsp.add(valor);
                            }
                        } else if (variableKey.startsWith("turno")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTurno.add(valor);
                            }
                        } else if (variableKey.startsWith("disp")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosDisp.add(valor);
                            }
                        }
                    });

                    // Crear cadenas de concatenación a partir de los valores únicos en los conjuntos
                    String noOrdenConcatenado = String.join("\n", valoresUnicosNoOrden);
                    String tamLoteConcatenado = String.join("\n", valoresUnicosTamLote);
                    String tamMtaConcatenado = String.join("\n", valoresUnicosTamMta);
                    String inspConcatenado = String.join("\n", valoresUnicosInsp);
                    String turnoConcatenado = String.join("\n", valoresUnicosTurno);
                    String dispConcatenado = String.join("\n", valoresUnicosDisp);

                    // Obtener el valor actual en la celda 
                    Cell cellNoOrden = validarCeldas(row, 2, "");
                    Cell cellTamLote = validarCeldas(row, 3, "");
                    Cell cellTamMta = validarCeldas(row, 4, "");
                    Cell cellInsp = validarCeldas(row, 5, "");
                    Cell cellTurno = validarCeldas(row, 6, "");
                    Cell cellDisp = validarCeldas(row, 7, "");

                    String valorActual = cellNoOrden.getStringCellValue();
                    String valorActual2 = cellTamLote.getStringCellValue();
                    String valorActual3 = cellTamMta.getStringCellValue();
                    String valorActual4 = cellInsp.getStringCellValue();
                    String valorActual5 = cellTurno.getStringCellValue();
                    String valorActual6 = cellDisp.getStringCellValue();

                    // Concatenar el valor actual con el nuevo valor de 'noOrden' (manteniendo los saltos de línea)
                    if (!valorActual.isEmpty()) {
                        noOrdenConcatenado = valorActual + "\n" + noOrdenConcatenado;
                    }

                    if (!valorActual2.isEmpty()) {
                        tamLoteConcatenado = valorActual2 + "\n" + tamLoteConcatenado;
                    }

                    if (!valorActual3.isEmpty()) {
                        tamMtaConcatenado = valorActual3 + "\n" + tamMtaConcatenado;
                    }

                    if (!valorActual4.isEmpty()) {
                        inspConcatenado = valorActual4 + "\n" + inspConcatenado;
                    }

                    if (!valorActual5.isEmpty()) {
                        turnoConcatenado = valorActual5 + "\n" + turnoConcatenado;
                    }

                    if (!valorActual6.isEmpty()) {
                        dispConcatenado = valorActual6 + "\n" + dispConcatenado;
                    }

                    // Se aplica el estilo a las celdas
                    cellNoOp.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellFecha.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellNoOrden.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellTamLote.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellTamMta.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellInsp.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellTurno.setCellStyle(formato.estiloCeldasRD(workbook));
                    cellDisp.setCellStyle(formato.estiloCeldasRD(workbook));

                    // Colocar el valor concatenado en la celda correspondiente
                    cellNoOrden.setCellValue(noOrdenConcatenado);
                    cellTamLote.setCellValue(tamLoteConcatenado);
                    cellTamMta.setCellValue(tamMtaConcatenado);
                    cellInsp.setCellValue(inspConcatenado);
                    cellTurno.setCellValue(turnoConcatenado);
                    cellDisp.setCellValue(dispConcatenado);
                }
            }
        }
    }

    private void agregarFilaInformacion(Sheet sheet, int fila, int columna, String valor) {
        Row row = sheet.getRow(fila); // Obtén la fila
        if (row == null) {
            row = sheet.createRow(fila); // Crea la fila si no existe
        }
        Cell cell = row.getCell(columna);
        if (cell == null) {
            cell = row.createCell(columna); // Crea la celda si no existe
        }
        cell.setCellValue(valor); // Modifica el valor de la celda
    }

    private Cell validarCeldas(Row row, int fila, String valor) {
        Cell celda = row.getCell(fila);
        if (celda == null) {
            celda = row.createCell(fila);
        }
        celda.setCellValue(valor);
        return celda;
    }

    private void procesarProcesos(XSSFSheet sheet, List<AceptacionPc1> ap1m) {
        for (int filaProcesos : FILAS_PROCESOS) {
            int filaBase = 12;
            for (int i = 1; i <= Integer.parseInt(ap1m.get(0).getNoOps()); i++) {
                Row rowNoOps = sheet.getRow(filaProcesos);
                Cell cellNoOps = rowNoOps.getCell(filaBase);
                cellNoOps.setCellValue("X");
                filaBase += 4;
            }
        }
    }

    private void procesarComponentes(XSSFSheet sheet, List<AceptacionPc1> ap1m) {
        for (int filaInfo : FILAS_INFO) {
            for (int indexColumna : COLUMNAS_COMPONENTE) {
                Row rowComponente = sheet.getRow(filaInfo);
                Cell cell = rowComponente.getCell(indexColumna);

                switch (indexColumna) {
                    case 13:
                        cell.setCellValue(ap1m.get(0).getComponente());
                        break;
                    case 20:
                        cell.setCellValue(ap1m.get(0).getNoParte());
                        break;
                    case 26:
                        cell.setCellValue(ap1m.get(0).getNoTroquel());
                        break;
                }
            }
        }
    }

    private TreeMap<String, TreeMap<String, Map<String, String>>> crearTreeMap(Comparator<String> comparator) {
        return new TreeMap<>(comparator);
    }

    private int calcularTamanoEntrada(TreeMap<String, TreeMap<String, Map<String, String>>> map) {
        return map.values().stream().mapToInt(TreeMap::size).sum();
    }

    private void ajustarFilasNecesarias(Sheet sheet2, int fila, int filasNecesarias, int columnaBase, List<String> listVariables, int filaD, int tamanoEntrada2) {
        if (filasNecesarias > 0) { // Inserta las filas necesarias en la hoja

            sheet2.shiftRows(fila + 1, sheet2.getLastRowNum() + 1, filasNecesarias, true, false);

            // Ajusta las filas base para reflejar las inserciones
            filaBaseNoOp1 += filasNecesarias;
            filaBaseNoOp3 += filasNecesarias;
            filaBaseNoOp4 += filasNecesarias;
            filaBaseNoOp5 += filasNecesarias;
            
            fila1 += filasNecesarias;
            fila3 += filasNecesarias;
            fila4 += filasNecesarias;
            fila5 += filasNecesarias;

            Row filaACopiar = sheet2.getRow(fila); // Obtener la fila que deseas copiar

            for (int i = 0; i < filasNecesarias; i++) {
                int filaNecesaria = fila + i + 1; // Fila actual
                Row nuevaFila = sheet2.createRow(filaNecesaria);

                copiarFilaConCombinacion(sheet2, filaACopiar, nuevaFila, columnaBase, listVariables); // Copia la fila deseada con combinación

                sheet2.setRowBreak(filaNecesaria); // Ajusta el salto de página en cada fila insertada
            }
        }
    }

    private void copiarFilaConCombinacion(Sheet sheet2, Row filaOrigen, Row filaNueva, int columnaBase, List<String> listVariables) {
        filaNueva.setHeight(filaOrigen.getHeight());

        for (Cell viejaCelda : filaOrigen) {
            int columnIndex = viejaCelda.getColumnIndex();
            Cell nuevaCelda = filaNueva.createCell(columnIndex);
            nuevaCelda.setCellStyle(viejaCelda.getCellStyle());

            if (columnIndex >= columnaBase && (columnIndex - columnaBase) % 2 == 0) {
                // Combina las celdas a partir de la columnaBase en incrementos de 2
                int mergedColumnIndex = columnIndex + 1;
                sheet2.addMergedRegion(new CellRangeAddress(filaNueva.getRowNum(), filaNueva.getRowNum(), columnIndex, mergedColumnIndex));
            }
        }
    }

    private void setValorCeldasHojaInstruccion(Sheet sheet, int pocisionFila, int pocisionCelda, String valor) {
        Row fila = sheet.getRow(pocisionFila); // Obtén la fila
        Cell celda = fila.getCell(pocisionCelda); // Obtén la celda en la fila
        celda.setCellValue(valor); // Modifica el valor de la celda
    }
}
