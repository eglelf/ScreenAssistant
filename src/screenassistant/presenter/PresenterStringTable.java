/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenassistant.presenter;

import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import screenassistant.Collections.StringTables;
import screenassistant.dao.DaoStringTable;
import screenassistant.dao.Diretorio;
import screenassistant.model.StringTable;
import screenassistant.view.ViewStringTable;

/**
 *
 * @author egle_ferreira
 */
public class PresenterStringTable {

    private ViewStringTable viewST;
    private DefaultTableModel tm;
    private StringTables st;
    private DaoStringTable daoST;
    boolean doubleClick;
    boolean windowsTop;

    public PresenterStringTable() {
        this.viewST = new ViewStringTable();
        //this.datST = new DaoStringTable("C:/Desenv/RAMO-ATMA/6-Projetos/DYNASTY/ATM_30/FonteHST/Bradesco/01.00/StringTable/StringsTable.properties");
        this.daoST = new DaoStringTable();
        this.st = new StringTables(this.daoST.getStringTable());
        this.st.sort();
        this.doubleClick = true;
        this.windowsTop = true;
        configurarTela();
    }

    public void configurarTela() {
        //Configurar Tabela
        JTable tb = createTable(false);
        carregarStringTableNaTabela(this.st.getStringTables());
        this.viewST.getScrollPaneTable().getViewport().add(tb);

        //Actions Liteners
        this.viewST.getTxtBusca().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                carregarStringTableNaTabela(buscar());
            }

        });

        this.viewST.getCbID().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarStringTableNaTabela(buscar());
            }
        });

        this.viewST.getCbDefaultString().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarStringTableNaTabela(buscar());
            }
        });

        this.viewST.getCbCaseSensitive().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarStringTableNaTabela(buscar());
            }
        });

        //Botões
        this.viewST.getBtnDoubleClick().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doubleClick) {
                    doubleClick = false;
                    Icon iconDoubleClickON = new ImageIcon(getClass().getResource("/screenassistant/data/doubleClick.png"));
                    viewST.getBtnDoubleClick().setIcon(iconDoubleClickON);
                } else {
                    doubleClick = true;
                    Icon iconDoubleClick = new ImageIcon(getClass().getResource("/screenassistant/data/doubleClickON.png"));
                    viewST.getBtnDoubleClick().setIcon(iconDoubleClick);
                }

                JTable tb = null;
                if (doubleClick) {
                    tb = createTable(false);
                    //carregarStringTableNaTabela(this.st.getStringTables());
                } else {
                    tb = createTable(true);
                }
                carregarStringTableNaTabela(buscar());
                viewST.getScrollPaneTable().getViewport().removeAll();
                viewST.getScrollPaneTable().getViewport().add(tb);
            }
        });

        this.viewST.getBtnWindowTop().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (windowsTop) {
                    windowsTop = false;
                    Icon iconWindowTopOn = new ImageIcon(getClass().getResource("/screenassistant/data/windowTopOff.png"));
                    viewST.getBtnWindowTop().setIcon(iconWindowTopOn);
                } else {
                    windowsTop = true;
                    Icon iconWindowTopOff = new ImageIcon(getClass().getResource("/screenassistant/data/windowTopOn.png"));
                    viewST.getBtnWindowTop().setIcon(iconWindowTopOff);
                }
                viewST.setAlwaysOnTop(windowsTop);
            }
        });

        this.viewST.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                st = new StringTables(daoST.getStringTable());
                st.sort();
                carregarStringTableNaTabela(st.getStringTables());
            }
        });

        this.viewST.getBtnOpenFolder().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        //Set Tela
        String title = (this.daoST.getDir().isEmpty()) ? "Screen Assistant" : "Screen Assistant - " + this.daoST.getDir();
        this.viewST.setAlwaysOnTop(windowsTop);
        this.viewST.setTitle(title);
        this.viewST.setVisible(true);
        this.viewST.setLocationRelativeTo(null);
    }

    private JTable createTable(boolean enableEdit) {

        Object colunas[] = {"ID", "Default String"};
        if (enableEdit) {
            tm = new DefaultTableModel(colunas, 0);
        } else {
            tm = new DefaultTableModel(colunas, 0) {
                @Override
                public boolean isCellEditable(int rowIndex, int mColIndex) {
                    return false;
                }
            };
        }

        JTable tb = new JTable(tm) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    JComponent jc = (JComponent) c;
                    jc.setToolTipText(getValueAt(row, column).toString());
                }
                return c;
            }
        };

        if (!enableEdit) {
            tb.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    doubleClickToCopy(mouseEvent);
                }
            });
        }

        return tb;
    }

    private void carregarStringTableNaTabela(List<StringTable> sts) {
        tm.setNumRows(0);

        sts.forEach((stringTable) -> {
            tm.addRow(new Object[]{stringTable.getKey(), stringTable.getValue()});
        });
    }

    public List<StringTable> buscar() {
        String txt = viewST.getTxtBusca().getText();
        boolean id = viewST.getCbID().isSelected();
        boolean ds = viewST.getCbDefaultString().isSelected();
        boolean cs = viewST.getCbCaseSensitive().isSelected();

        return this.st.buscar(txt, id, ds, cs);
    }

    public void enviarParaAreaDeTranferencia(String txt) {
        StringSelection stringSelection = new StringSelection(txt);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public void doubleClickToCopy(MouseEvent mouseEvent) {
        JTable table = (JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        int colum = table.columnAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
            String value = (String) tm.getValueAt(row, colum);
            System.out.println("clicou em: " + value);
            enviarParaAreaDeTranferencia(value);
        }
    }

    public boolean openFile() {
        Diretorio diretorio = new Diretorio();
        this.viewST.setAlwaysOnTop(false);
        try {
            String dir = diretorio.getDirectoryFileToOpen("StringTable.properties", "properties");
            this.daoST.setDir(dir);
            this.st = new StringTables(this.daoST.getStringTable());
            this.st.sort();
            carregarStringTableNaTabela(this.st.getStringTables());
            this.viewST.setTitle("Screen Assistant - " + this.daoST.getDir());
            this.viewST.setAlwaysOnTop(windowsTop);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        this.st = new StringTables();
        carregarStringTableNaTabela(this.st.getStringTables());
        this.viewST.setTitle("Screen Assistant");
        this.viewST.setAlwaysOnTop(windowsTop);
        return false;
    }

}
